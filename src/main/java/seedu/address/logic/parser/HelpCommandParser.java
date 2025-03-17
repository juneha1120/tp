package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new HelpCommand object
 */
public class HelpCommandParser implements Parser<HelpCommand> {

    private static final String EMPTY_COMMAND = "";

    /**
     * Parses the given {@code String} of arguments in the context of the HelpCommand
     * and returns a HelpCommand object for execution.
     */
    public HelpCommand parse(String args) throws ParseException {
        return switch (args.trim()) {
        case EMPTY_COMMAND -> new HelpCommand();
        case AddCommand.COMMAND_WORD -> new HelpCommand(AddCommand.COMMAND_WORD, AddCommand.MESSAGE_USAGE,
                "Adds a new contact to the database with optional details.");
        case DeleteCommand.COMMAND_WORD -> new HelpCommand(DeleteCommand.COMMAND_WORD, DeleteCommand.MESSAGE_USAGE,
                "Deletes a existing contact from the database with optional details.");
        case ListCommand.COMMAND_WORD -> new HelpCommand(ListCommand.COMMAND_WORD, ListCommand.COMMAND_WORD,
                "Lists out the contact in the database with optional category.");
        default ->
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        };
    }

}
