package trackup.logic.parser;

import static trackup.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import trackup.logic.commands.SortCommand;
import trackup.logic.parser.exceptions.ParseException;
import trackup.model.person.comparators.NameComparator;
import trackup.model.person.comparators.PhoneComparator;


/**
 * Parses input arguments and creates a new HelpCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    private static final String EMPTY_COMMAND = "";

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     */
    public SortCommand parse(String args) throws ParseException {
        return switch (args.trim()) {
            case "-n" -> new SortCommand(new NameComparator());
            case "-p" -> new SortCommand(new PhoneComparator());
            default ->
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        };
    }

}
