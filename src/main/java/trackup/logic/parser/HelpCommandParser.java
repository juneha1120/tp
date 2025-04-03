package trackup.logic.parser;

import static trackup.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import trackup.logic.commands.AddCommand;
import trackup.logic.commands.AddEventCommand;
import trackup.logic.commands.ClearCommand;
import trackup.logic.commands.DeleteByCommand;
import trackup.logic.commands.DeleteCommand;
import trackup.logic.commands.DeleteEventCommand;
import trackup.logic.commands.EditCommand;
import trackup.logic.commands.ExitCommand;
import trackup.logic.commands.FindCommand;
import trackup.logic.commands.HelpCommand;
import trackup.logic.commands.ListCommand;
import trackup.logic.commands.SearchCommand;
import trackup.logic.commands.SortCommand;
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
        case AddCommand.COMMAND_WORD -> new HelpCommand(AddCommand.COMMAND_WORD,
                AddCommand.MESSAGE_USAGE,
                "Adds a contact to TrackUp.");
        case AddEventCommand.COMMAND_WORD -> new HelpCommand(AddEventCommand.COMMAND_WORD,
                AddEventCommand.MESSAGE_USAGE,
                "Adds an event to TrackUp's calendar.");
        case ClearCommand.COMMAND_WORD -> new HelpCommand(ClearCommand.COMMAND_WORD,
                ClearCommand.COMMAND_WORD,
                "Clears all contacts from TrackUp.");
        case DeleteCommand.COMMAND_WORD -> new HelpCommand(DeleteCommand.COMMAND_WORD,
                DeleteCommand.MESSAGE_USAGE,
                "Deletes the specified contact from TrackUp.");
        case DeleteByCommand.COMMAND_WORD -> new HelpCommand(DeleteByCommand.COMMAND_WORD,
                DeleteByCommand.MESSAGE_USAGE,
                "Deletes a contact from TrackUp using one or more attributes.");
        case DeleteEventCommand.COMMAND_WORD -> new HelpCommand(DeleteEventCommand.COMMAND_WORD,
                DeleteEventCommand.MESSAGE_USAGE,
                "Deletes events from TrackUp's calendar based on specified filters.");
        case EditCommand.COMMAND_WORD -> new HelpCommand(EditCommand.COMMAND_WORD,
                EditCommand.MESSAGE_USAGE,
                "Edits an existing person in TrackUp.");
        case ExitCommand.COMMAND_WORD -> new HelpCommand(ExitCommand.COMMAND_WORD,
                ExitCommand.COMMAND_WORD,
                "Exits TrackUp.");
        case FindCommand.COMMAND_WORD -> new HelpCommand(FindCommand.COMMAND_WORD,
                FindCommand.MESSAGE_USAGE,
                "Finds and lists all contacts in TrackUp whose names contain any of the specified keywords.");
        case ListCommand.COMMAND_WORD -> new HelpCommand(ListCommand.COMMAND_WORD,
                ListCommand.COMMAND_WORD,
                "Displays all contacts in TrackUp, optionally filtering by category.");
        case SearchCommand.COMMAND_WORD -> new HelpCommand(SearchCommand.COMMAND_WORD,
                SearchCommand.MESSAGE_USAGE,
                "Finds persons whose attributes contain the specified keyword");
        case SortCommand.COMMAND_WORD -> new HelpCommand(SortCommand.COMMAND_WORD,
                SortCommand.MESSAGE_USAGE,
                "Sorts current displayed list according to given Prefix");
        default ->
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        HelpCommand.MESSAGE_USAGE));
        };
    }

}
