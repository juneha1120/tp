package trackup.logic.parser;

import static trackup.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static trackup.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static trackup.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static trackup.logic.parser.CliSyntax.PREFIX_EMAIL;
import static trackup.logic.parser.CliSyntax.PREFIX_NAME;
import static trackup.logic.parser.CliSyntax.PREFIX_PHONE;
import static trackup.logic.parser.CliSyntax.PREFIX_TAG;

import javafx.util.Pair;
import trackup.logic.commands.SortCommand;
import trackup.logic.parser.exceptions.ParseException;
import trackup.model.person.comparators.AscendingComparators;
import trackup.model.person.comparators.DescendingComparators;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;


/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     */
    public SortCommand parse(String args) throws ParseException {

        Pair<Prefix, String> nameVal = getPrefixValue(PREFIX_NAME, args);
        Pair<Prefix, String> phoneVal = getPrefixValue(PREFIX_PHONE, args);
        Pair<Prefix, String> emailVal = getPrefixValue(PREFIX_EMAIL, args);
        Pair<Prefix, String> addressVal = getPrefixValue(PREFIX_ADDRESS, args);
        Pair<Prefix, String> tagVal = getPrefixValue(PREFIX_TAG, args);
        Pair<Prefix, String> categoryVal = getPrefixValue(PREFIX_CATEGORY, args);

        Stream<Pair<Prefix, String>> filteredValues = Stream
                .of(nameVal, phoneVal, emailVal, addressVal, tagVal, categoryVal)
                .filter(x -> x.getValue() != null);
        if (filteredValues.count() != 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        filteredValues = Stream
                .of(nameVal, phoneVal, emailVal, addressVal, tagVal, categoryVal)
                .filter(x -> x.getValue() != null);
        Pair<Prefix, String> firstVal = filteredValues.findFirst().get();

        boolean isDescending = Boolean.parseBoolean(firstVal.getValue());
        Prefix key = firstVal.getKey();
        if (isDescending) {
            if (key.equals(PREFIX_NAME)) {
                return new SortCommand(new DescendingComparators.NameComparator());
            } else if (key.equals(PREFIX_PHONE)) {
                return new SortCommand(new DescendingComparators.PhoneComparator());
            } else if (key.equals(PREFIX_EMAIL)) {
                return new SortCommand(new DescendingComparators.EmailComparator());
            } else if (key.equals(PREFIX_ADDRESS)) {
                return new SortCommand(new DescendingComparators.AddressComparator());
            } else if (key.equals(PREFIX_TAG)) {
                return new SortCommand(new DescendingComparators.TagComparator());
            } else if (key.equals(PREFIX_CATEGORY)) {
                return new SortCommand(new DescendingComparators.CategoryComparator());
            }
        }
        if (key.equals(PREFIX_NAME)) {
            return new SortCommand(new AscendingComparators.NameComparator());
        } else if (key.equals(PREFIX_PHONE)) {
            return new SortCommand(new AscendingComparators.PhoneComparator());
        } else if (key.equals(PREFIX_EMAIL)) {
            return new SortCommand(new AscendingComparators.EmailComparator());
        } else if (key.equals(PREFIX_ADDRESS)) {
            return new SortCommand(new AscendingComparators.AddressComparator());
        } else if (key.equals(PREFIX_TAG)) {
            return new SortCommand(new AscendingComparators.TagComparator());
        } else if (key.equals(PREFIX_CATEGORY)) {
            return new SortCommand(new AscendingComparators.CategoryComparator());
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }

    private static Pair<Prefix, String> getPrefixValue(Prefix prefix, String args) {
        Optional<String> value = ArgumentTokenizer.tokenize(args, prefix).getValue(prefix);
        return new Pair<>(prefix, value.orElse(null));
    }


}
