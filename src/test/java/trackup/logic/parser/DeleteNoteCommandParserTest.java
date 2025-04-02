package trackup.logic.parser;

import static trackup.logic.commands.DeleteNoteCommand.MESSAGE_USAGE;
import static trackup.logic.parser.CommandParserTestUtil.assertParseFailure;
import static trackup.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import trackup.commons.core.index.Index;
import trackup.logic.commands.DeleteNoteCommand;

public class DeleteNoteCommandParserTest {

    private final DeleteNoteCommandParser parser = new DeleteNoteCommandParser();

    @Test
    public void parse_validInput_success() {
        String userInput = "1 2";
        DeleteNoteCommand expectedCommand = new DeleteNoteCommand(Index.fromOneBased(1), Index.fromOneBased(2));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validInputWithExtraSpaces_success() {
        String userInput = "   3     1   ";
        DeleteNoteCommand expectedCommand = new DeleteNoteCommand(Index.fromOneBased(3), Index.fromOneBased(1));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingNoteIndex_failure() {
        assertParseFailure(parser, "1",
                String.format(trackup.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_missingAllArguments_failure() {
        assertParseFailure(parser, "",
                String.format(trackup.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_nonNumericArguments_failure() {
        assertParseFailure(parser, "one two",
                String.format(trackup.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_onlyWhitespace_failure() {
        assertParseFailure(parser, "    ",
                String.format(trackup.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_negativeIndices_failure() {
        assertParseFailure(parser, "-1 -2",
                String.format(trackup.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }
}
