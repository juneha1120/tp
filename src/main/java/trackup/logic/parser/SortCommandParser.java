package trackup.logic.parser;

import static trackup.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static trackup.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static trackup.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static trackup.logic.parser.CliSyntax.PREFIX_EMAIL;
import static trackup.logic.parser.CliSyntax.PREFIX_NAME;
import static trackup.logic.parser.CliSyntax.PREFIX_PHONE;
import static trackup.logic.parser.CliSyntax.PREFIX_TAG;

import trackup.logic.commands.SortCommand;
import trackup.logic.parser.exceptions.ParseException;
import trackup.model.person.comparators.*;

import java.util.Optional;


/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     */
    public SortCommand parse(String args) throws ParseException {
        if (getPrefixValue(args, PREFIX_NAME).isPresent()) {
            return new SortCommand(new NameComparator());
        } else if (getPrefixValue(args, PREFIX_PHONE).isPresent()) {
            return new SortCommand(new PhoneComparator());
        } else if (getPrefixValue(args, PREFIX_EMAIL).isPresent()) {
            return new SortCommand(new EmailComparator());
        } else if (getPrefixValue(args, PREFIX_ADDRESS).isPresent()) {
            return new SortCommand(new AddressComparator());
        } else if (getPrefixValue(args, PREFIX_TAG).isPresent()) {
            return new SortCommand(new TagComparator());
        } else if (getPrefixValue(args, PREFIX_CATEGORY).isPresent()) {
            return new SortCommand(new CategoryComparator());
        }
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    private static Optional<String> getPrefixValue(String args, Prefix prefix) {
        return ArgumentTokenizer.tokenize(args, prefix).getValue(prefix);
    }


}
