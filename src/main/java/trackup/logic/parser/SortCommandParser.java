package trackup.logic.parser;

import static java.util.Objects.requireNonNull;
import static trackup.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static trackup.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static trackup.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static trackup.logic.parser.CliSyntax.PREFIX_EMAIL;
import static trackup.logic.parser.CliSyntax.PREFIX_NAME;
import static trackup.logic.parser.CliSyntax.PREFIX_PHONE;
import static trackup.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import javafx.util.Pair;
import trackup.logic.commands.SortCommand;
import trackup.logic.parser.exceptions.ParseException;
import trackup.model.person.Comparators;
import trackup.model.person.Person;


/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    private static final String INVALID_SORT_VALUE_MESSAGE =
            "Invalid sort order for prefix '%s': '%s'. Expected 'true' or 'false' (case-insensitive).";

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG, PREFIX_CATEGORY);

        Map<Prefix, Comparator<Person>> comparatorMap = Map.of(
                PREFIX_NAME, Comparators.NAME_COMPARATOR,
                PREFIX_PHONE, Comparators.PHONE_COMPARATOR,
                PREFIX_EMAIL, Comparators.EMAIL_COMPARATOR,
                PREFIX_ADDRESS, Comparators.ADDRESS_COMPARATOR,
                PREFIX_TAG, Comparators.TAG_COMPARATOR,
                PREFIX_CATEGORY, Comparators.CATEGORY_COMPARATOR
        );

        List<Pair<Integer, Comparator<Person>>> comparators = new ArrayList<>();

        for (Prefix prefix : comparatorMap.keySet()) {
            Optional<String> valueOpt = argMultimap.getValue(prefix);
            if (valueOpt.isPresent()) {
                String rawValue = valueOpt.get().strip();
                String value = rawValue.toLowerCase();
                if (!value.equals("true") && !value.equals("false")) {
                    throw new ParseException(String.format(INVALID_SORT_VALUE_MESSAGE, prefix.getPrefix(), rawValue));
                }

                boolean isDescending = Boolean.parseBoolean(value);
                Comparator<Person> comparator = comparatorMap.get(prefix);
                Integer index = args.indexOf(prefix.getPrefix());
                comparators.add(new Pair<>(index, isDescending ? comparator.reversed() : comparator));
            }
        }

        if (comparators.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        comparators.sort(Comparator.comparingInt(Pair::getKey));

        Comparator<Person> finalComparator = comparators.stream()
                .map(Pair::getValue)
                .reduce(Comparator::thenComparing)
                .orElseThrow(() -> new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE)));

        return new SortCommand(finalComparator);
    }

    /**
     * Returns A Pair of Prefix and value by extracting the {@param args}
     */
    private static Pair<Prefix, String> getPrefixValue(Prefix prefix, String args) {
        Optional<String> value = ArgumentTokenizer.tokenize(args, prefix).getValue(prefix);
        return new Pair<>(prefix, value.orElse(null));
    }


}
