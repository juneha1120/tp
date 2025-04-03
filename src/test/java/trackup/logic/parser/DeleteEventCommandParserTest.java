package trackup.logic.parser;

import static trackup.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static trackup.logic.commands.CommandTestUtil.EVENT_CONTACT_DESC_1;
import static trackup.logic.commands.CommandTestUtil.EVENT_CONTACT_DESC_2;
import static trackup.logic.commands.CommandTestUtil.EVENT_END_DESC;
import static trackup.logic.commands.CommandTestUtil.EVENT_START_DESC;
import static trackup.logic.commands.CommandTestUtil.EVENT_TITLE_DESC;
import static trackup.logic.commands.CommandTestUtil.INVALID_EVENT_CONTACT_DESC;
import static trackup.logic.commands.CommandTestUtil.INVALID_EVENT_END_DESC;
import static trackup.logic.commands.CommandTestUtil.INVALID_EVENT_START_DESC;
import static trackup.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static trackup.logic.commands.CommandTestUtil.VALID_EVENT_INDEX_1;
import static trackup.logic.commands.CommandTestUtil.VALID_EVENT_INDEX_2;
import static trackup.logic.parser.CommandParserTestUtil.assertParseFailure;
import static trackup.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static trackup.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDateTime;
import java.util.Set;

import org.junit.jupiter.api.Test;

import trackup.commons.core.index.Index;
import trackup.logic.commands.DeleteEventCommand;

public class DeleteEventCommandParserTest {

    private final DeleteEventCommandParser parser = new DeleteEventCommandParser();

    @Test
    public void parse_validTitleOnly_success() {
        DeleteEventCommand expected = new DeleteEventCommand("Meeting", null, null, Set.of());
        assertParseSuccess(parser, EVENT_TITLE_DESC, expected);
    }

    @Test
    public void parse_validStartAndEnd_success() {
        LocalDateTime start = LocalDateTime.of(2025, 4, 1, 14, 0);
        LocalDateTime end = LocalDateTime.of(2025, 4, 1, 15, 0);
        DeleteEventCommand expected = new DeleteEventCommand(null, start, end, Set.of());
        assertParseSuccess(parser, EVENT_START_DESC + EVENT_END_DESC, expected);
    }

    @Test
    public void parse_validContacts_success() {
        String userInput = EVENT_CONTACT_DESC_1 + EVENT_CONTACT_DESC_2;

        Set<Index> expectedIndexes = Set.of(
                Index.fromOneBased(Integer.parseInt(VALID_EVENT_INDEX_1)),
                Index.fromOneBased(Integer.parseInt(VALID_EVENT_INDEX_2))
        );

        DeleteEventCommand expectedCommand = new DeleteEventCommand(null, null, null, expectedIndexes);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_allFilters_success() {
        LocalDateTime start = LocalDateTime.of(2025, 4, 1, 14, 0);
        LocalDateTime end = LocalDateTime.of(2025, 4, 1, 15, 0);
        DeleteEventCommand expected = new DeleteEventCommand("Meeting", start, end, Set.of(INDEX_FIRST_PERSON));

        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + EVENT_TITLE_DESC + EVENT_START_DESC + EVENT_END_DESC + EVENT_CONTACT_DESC_1,
                expected);
    }

    @Test
    public void parse_missingAllFields_failure() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidStart_failure() {
        assertParseFailure(parser, INVALID_EVENT_START_DESC,
                ParserUtil.MESSAGE_INVALID_DATE_TIME);
    }

    @Test
    public void parse_invalidEnd_failure() {
        assertParseFailure(parser, INVALID_EVENT_END_DESC,
                ParserUtil.MESSAGE_INVALID_DATE_TIME);
    }

    @Test
    public void parse_invalidContact_failure() {
        assertParseFailure(parser, INVALID_EVENT_CONTACT_DESC,
                ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_duplicateTitle_failure() {
        String input = EVENT_TITLE_DESC + " " + EVENT_TITLE_DESC;
        assertParseFailure(parser, input,
                "Multiple values specified for the following single-valued field(s): -t ");
    }

    @Test
    public void parse_duplicateStart_failure() {
        String input = EVENT_START_DESC + " " + EVENT_START_DESC;
        assertParseFailure(parser, input,
                "Multiple values specified for the following single-valued field(s): -s ");
    }

    @Test
    public void parse_duplicateEnd_failure() {
        String input = EVENT_END_DESC + " " + EVENT_END_DESC;
        assertParseFailure(parser, input,
                "Multiple values specified for the following single-valued field(s): -e ");
    }
}
