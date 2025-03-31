package trackup.logic.parser;

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

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {

        Map<Prefix, Function<Boolean, Comparator<Person>>> comparatorMap = Map.of(
                PREFIX_NAME, isDesc -> isDesc ? Comparators.NAME_COMPARATOR.reversed()
                        : Comparators.NAME_COMPARATOR,
                PREFIX_PHONE, isDesc -> isDesc ? Comparators.PHONE_COMPARATOR.reversed()
                        : Comparators.PHONE_COMPARATOR,
                PREFIX_EMAIL, isDesc -> isDesc ? Comparators.EMAIL_COMPARATOR.reversed()
                        : Comparators.EMAIL_COMPARATOR,
                PREFIX_ADDRESS, isDesc -> isDesc ? Comparators.ADDRESS_COMPARATOR.reversed()
                        : Comparators.ADDRESS_COMPARATOR,
                PREFIX_TAG, isDesc -> isDesc ? Comparators.TAG_COMPARATOR.reversed()
                        : Comparators.TAG_COMPARATOR,
                PREFIX_CATEGORY, isDesc -> isDesc ? Comparators.CATEGORY_COMPARATOR.reversed()
                        : Comparators.CATEGORY_COMPARATOR
        );

        List<Pair<Prefix, Comparator<Person>>> comparators = new ArrayList<>();

        for (Prefix prefix : comparatorMap.keySet()) {
            Pair<Prefix, String> prefixValue = getPrefixValue(prefix, args);
            if (prefixValue.getValue() != null) {
                int index = args.indexOf(prefix.getPrefix());
                if (index != -1) {
                    boolean isDescending = Boolean.parseBoolean(prefixValue.getValue());
                    comparators.add(new Pair<>(prefix, comparatorMap.get(prefix).apply(isDescending)));
                }
            }
        }

        if (comparators.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        comparators.sort(Comparator.comparingInt(p -> args.indexOf(p.getKey().getPrefix())));
        Comparator<Person> finalComparator = comparators.get(0).getValue();
        for (int i = 1; i < comparators.size(); i++) {
            finalComparator = finalComparator.thenComparing(comparators.get(i).getValue());
        }

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
