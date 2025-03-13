package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class HelpCommandParserTest {

    private final HelpCommandParser parser = new HelpCommandParser();

    @Test
    public void parse_validAddCommand_returnsHelpCommand() throws ParseException {
        String args = AddCommand.COMMAND_WORD;
        HelpCommand expectedCommand = new HelpCommand(AddCommand.COMMAND_WORD, AddCommand.MESSAGE_USAGE,
                "Adds a new contact to the database with optional details.");
        assertEquals(expectedCommand, parser.parse(args));
    }

    @Test
    public void parse_validListCommand_returnsHelpCommand() throws ParseException {
        String args = ListCommand.COMMAND_WORD;
        HelpCommand expectedCommand = new HelpCommand(ListCommand.COMMAND_WORD, ListCommand.COMMAND_WORD,
                "Lists out the contact in the database with optional category.");
        assertEquals(expectedCommand, parser.parse(args));
    }

    @Test
    public void parse_emptyArgs_returnsDefaultHelpCommand() throws ParseException {
        String args = "";
        HelpCommand expectedCommand = new HelpCommand();
        assertEquals(expectedCommand, parser.parse(args));
    }

    @Test
    public void parse_invalidCommand_throwsParseException() {
        String args = "invalidCommand";
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(args));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), exception.getMessage());
    }

    @Test
    public void parse_whitespaceArgs_returnsDefaultHelpCommand() throws ParseException {
        String args = "   ";
        HelpCommand expectedCommand = new HelpCommand();
        assertEquals(expectedCommand, parser.parse(args));
    }
}
