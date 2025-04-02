package trackup.logic.parser;

import static trackup.logic.commands.AddNoteCommand.MESSAGE_USAGE;
import static trackup.logic.parser.CommandParserTestUtil.assertParseFailure;
import static trackup.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import trackup.commons.core.index.Index;
import trackup.logic.commands.AddNoteCommand;

public class AddNoteCommandParserTest {

    private final AddNoteCommandParser parser = new AddNoteCommandParser();

    @Test
    public void parse_validInput_success() {
        String userInput = "1 Follow up next week after demo";
        AddNoteCommand expectedCommand = new AddNoteCommand(Index.fromOneBased(1), "Follow up next week after demo");
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validInputWithExtraSpaces_success() {
        String userInput = "   2    Note with   extra   spaces  ";
        AddNoteCommand expectedCommand = new AddNoteCommand(Index.fromOneBased(2), "Note with   extra   spaces");
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingIndex_failure() {
        assertParseFailure(parser, "Follow up note",
                String.format(trackup.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_missingNote_failure() {
        assertParseFailure(parser, "1",
                String.format(trackup.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_failure() {
        assertParseFailure(parser, "abc Note content",
                String.format(trackup.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyNote_failure() {
        assertParseFailure(parser, "1    ",
                String.format(trackup.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }
}
