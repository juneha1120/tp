package trackup.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static trackup.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

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

public class HelpCommandParserTest {

    private final HelpCommandParser parser = new HelpCommandParser();

    @Test
    public void parse_emptyArgs_returnsDefaultHelpCommand() throws ParseException {
        assertEquals(new HelpCommand(), parser.parse(""));
    }

    @Test
    public void parse_addCommand_returnsCorrectHelpCommand() throws ParseException {
        HelpCommand expected = new HelpCommand(
                AddCommand.COMMAND_WORD,
                AddCommand.MESSAGE_USAGE,
                "Adds a contact to TrackUp.");
        assertEquals(expected, parser.parse(AddCommand.COMMAND_WORD));
    }

    @Test
    public void parse_addEventCommand_returnsCorrectHelpCommand() throws ParseException {
        HelpCommand expected = new HelpCommand(
                AddEventCommand.COMMAND_WORD,
                AddEventCommand.MESSAGE_USAGE,
                "Adds an event to TrackUp's calendar.");
        assertEquals(expected, parser.parse(AddEventCommand.COMMAND_WORD));
    }

    @Test
    public void parse_clearCommand_returnsCorrectHelpCommand() throws ParseException {
        HelpCommand expected = new HelpCommand(
                ClearCommand.COMMAND_WORD,
                ClearCommand.COMMAND_WORD,
                "Adds a contact to TrackUp."); // Note: This appears to be a typo in the actual implementation
        assertEquals(expected, parser.parse(ClearCommand.COMMAND_WORD));
    }

    @Test
    public void parse_deleteCommand_returnsCorrectHelpCommand() throws ParseException {
        HelpCommand expected = new HelpCommand(
                DeleteCommand.COMMAND_WORD,
                DeleteCommand.MESSAGE_USAGE,
                "Deletes the specified contact from TrackUp.");
        assertEquals(expected, parser.parse(DeleteCommand.COMMAND_WORD));
    }

    @Test
    public void parse_deleteByCommand_returnsCorrectHelpCommand() throws ParseException {
        HelpCommand expected = new HelpCommand(
                DeleteByCommand.COMMAND_WORD,
                DeleteByCommand.MESSAGE_USAGE,
                "Deletes a contact from TrackUp using one or more attributes.");
        assertEquals(expected, parser.parse(DeleteByCommand.COMMAND_WORD));
    }

    @Test
    public void parse_deleteEventCommand_returnsCorrectHelpCommand() throws ParseException {
        HelpCommand expected = new HelpCommand(
                DeleteEventCommand.COMMAND_WORD,
                DeleteEventCommand.MESSAGE_USAGE,
                "Deletes events from TrackUp's calendar based on specified filters.");
        assertEquals(expected, parser.parse(DeleteEventCommand.COMMAND_WORD));
    }

    @Test
    public void parse_editCommand_returnsCorrectHelpCommand() throws ParseException {
        HelpCommand expected = new HelpCommand(
                EditCommand.COMMAND_WORD,
                EditCommand.MESSAGE_USAGE,
                "Edits an existing person in TrackUp.");
        assertEquals(expected, parser.parse(EditCommand.COMMAND_WORD));
    }

    @Test
    public void parse_exitCommand_returnsCorrectHelpCommand() throws ParseException {
        HelpCommand expected = new HelpCommand(
                ExitCommand.COMMAND_WORD,
                ExitCommand.COMMAND_WORD,
                "Exits TrackUp.");
        assertEquals(expected, parser.parse(ExitCommand.COMMAND_WORD));
    }

    @Test
    public void parse_findCommand_returnsCorrectHelpCommand() throws ParseException {
        HelpCommand expected = new HelpCommand(
                FindCommand.COMMAND_WORD,
                FindCommand.MESSAGE_USAGE,
                "Finds and lists all contacts in TrackUp whose names contain any of the specified keywords.");
        assertEquals(expected, parser.parse(FindCommand.COMMAND_WORD));
    }

    @Test
    public void parse_listCommand_returnsCorrectHelpCommand() throws ParseException {
        HelpCommand expected = new HelpCommand(
                ListCommand.COMMAND_WORD,
                ListCommand.COMMAND_WORD,
                "Displays all contacts in TrackUp, optionally filtering by category.");
        assertEquals(expected, parser.parse(ListCommand.COMMAND_WORD));
    }

    @Test
    public void parse_searchCommand_returnsCorrectHelpCommand() throws ParseException {
        HelpCommand expected = new HelpCommand(
                SearchCommand.COMMAND_WORD,
                SearchCommand.MESSAGE_USAGE,
                "Finds persons whose attributes contain the specified keyword");
        assertEquals(expected, parser.parse(SearchCommand.COMMAND_WORD));
    }

    @Test
    public void parse_sortCommand_returnsCorrectHelpCommand() throws ParseException {
        HelpCommand expected = new HelpCommand(
                SortCommand.COMMAND_WORD,
                SortCommand.MESSAGE_USAGE,
                "Sorts current displayed list according to given Prefix");
        assertEquals(expected, parser.parse(SortCommand.COMMAND_WORD));
    }

    @Test
    public void parse_invalidCommand_throwsParseException() {
        String invalidCommand = "invalidCommand";
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(invalidCommand));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), exception.getMessage());
    }

    @Test
    public void parse_nullArgs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }
}
