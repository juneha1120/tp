package trackup.logic.parser;

import static trackup.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import trackup.logic.commands.AddCommand;
import trackup.logic.commands.AddEventCommand;
import trackup.logic.commands.AddNoteCommand;
import trackup.logic.commands.ClearCommand;
import trackup.logic.commands.DeleteByCommand;
import trackup.logic.commands.DeleteCommand;
import trackup.logic.commands.DeleteEventCommand;
import trackup.logic.commands.DeleteNoteCommand;
import trackup.logic.commands.EditCommand;
import trackup.logic.commands.ExitCommand;
import trackup.logic.commands.FindCommand;
import trackup.logic.commands.HelpCommand;
import trackup.logic.commands.ListCommand;
import trackup.logic.commands.SearchCommand;
import trackup.logic.commands.SortCommand;
import trackup.logic.commands.ToggleCommand;
import trackup.logic.parser.exceptions.ParseException;


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
        case AddCommand.COMMAND_WORD -> new HelpCommand(AddCommand.MESSAGE_USAGE);
        case DeleteCommand.COMMAND_WORD -> new HelpCommand(DeleteCommand.MESSAGE_USAGE);
        case DeleteByCommand.COMMAND_WORD -> new HelpCommand(DeleteByCommand.MESSAGE_USAGE);
        case EditCommand.COMMAND_WORD -> new HelpCommand(EditCommand.MESSAGE_USAGE);
        case ListCommand.COMMAND_WORD -> new HelpCommand(ListCommand.MESSAGE_USAGE);
        case SortCommand.COMMAND_WORD -> new HelpCommand(SortCommand.MESSAGE_USAGE);
        case FindCommand.COMMAND_WORD -> new HelpCommand(FindCommand.MESSAGE_USAGE);
        case SearchCommand.COMMAND_WORD -> new HelpCommand(SearchCommand.MESSAGE_USAGE);
        case AddEventCommand.COMMAND_WORD -> new HelpCommand(AddEventCommand.MESSAGE_USAGE);
        case DeleteEventCommand.COMMAND_WORD -> new HelpCommand(DeleteEventCommand.MESSAGE_USAGE);
        case AddNoteCommand.COMMAND_WORD -> new HelpCommand(AddNoteCommand.MESSAGE_USAGE);
        case DeleteNoteCommand.COMMAND_WORD -> new HelpCommand(DeleteNoteCommand.MESSAGE_USAGE);
        case ToggleCommand.COMMAND_WORD -> new HelpCommand(ToggleCommand.MESSAGE_USAGE);
        case ClearCommand.COMMAND_WORD -> new HelpCommand(ClearCommand.MESSAGE_USAGE);
        case ExitCommand.COMMAND_WORD -> new HelpCommand(ExitCommand.MESSAGE_USAGE);
        default ->
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        HelpCommand.MESSAGE_USAGE));
        };
    }

}
