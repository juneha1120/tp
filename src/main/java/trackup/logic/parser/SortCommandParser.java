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



/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     */
    public SortCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_TAG, PREFIX_CATEGORY);
        if (isPrefixPresent(argMultimap, PREFIX_NAME)) {
            return new SortCommand(new NameComparator());
        } else if (isPrefixPresent(argMultimap, PREFIX_PHONE)) {
            return new SortCommand(new PhoneComparator());
        } else if (isPrefixPresent(argMultimap, PREFIX_EMAIL)) {
            return new SortCommand(new EmailComparator());
        } else if (isPrefixPresent(argMultimap, PREFIX_ADDRESS)) {
            return new SortCommand(new AddressComparator());
        } else if (isPrefixPresent(argMultimap, PREFIX_TAG)) {
            return new SortCommand(new TagComparator());
        } else if (isPrefixPresent(argMultimap, PREFIX_CATEGORY)) {
            return new SortCommand(new CategoryComparator());
        }
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isPrefixPresent(ArgumentMultimap argumentMultimap, Prefix prefix) {
        return argumentMultimap.getValue(prefix).isPresent();
    }


}
