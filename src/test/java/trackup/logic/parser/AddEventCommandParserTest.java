package trackup.logic.parser;

import static trackup.logic.Messages.MESSAGE_END_BEFORE_START;
import static trackup.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static trackup.logic.commands.AddEventCommand.MESSAGE_USAGE;
import static trackup.logic.commands.CommandTestUtil.EVENT_CONTACT_DESC_1;
import static trackup.logic.commands.CommandTestUtil.EVENT_CONTACT_DESC_2;
import static trackup.logic.commands.CommandTestUtil.EVENT_END_DESC;
import static trackup.logic.commands.CommandTestUtil.EVENT_START_DESC;
import static trackup.logic.commands.CommandTestUtil.EVENT_TITLE_DESC;
import static trackup.logic.commands.CommandTestUtil.INVALID_EVENT_CONTACT_DESC;
import static trackup.logic.commands.CommandTestUtil.INVALID_EVENT_END_DESC;
import static trackup.logic.commands.CommandTestUtil.INVALID_EVENT_START_DESC;
import static trackup.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static trackup.logic.parser.CommandParserTestUtil.assertParseFailure;
import static trackup.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDateTime;
import java.util.Set;

import org.junit.jupiter.api.Test;

import trackup.commons.core.index.Index;
import trackup.logic.Messages;
import trackup.logic.commands.AddEventCommand;

public class AddEventCommandParserTest {

    private final AddEventCommandParser parser = new AddEventCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Set<Index> expectedIndexes = Set.of(Index.fromOneBased(1), Index.fromOneBased(3));
        LocalDateTime expectedStart = LocalDateTime.of(2025, 4, 1, 14, 0);
        LocalDateTime expectedEnd = LocalDateTime.of(2025, 4, 1, 15, 0);

        AddEventCommand expectedCommand = new AddEventCommand(
                "Meeting", expectedStart, expectedEnd, expectedIndexes
        );

        assertParseSuccess(parser,
                EVENT_TITLE_DESC + EVENT_START_DESC + EVENT_END_DESC + EVENT_CONTACT_DESC_1 + EVENT_CONTACT_DESC_2,
                expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryFields_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);

        // Missing title
        assertParseFailure(parser,
                EVENT_START_DESC + EVENT_END_DESC + EVENT_CONTACT_DESC_1, expectedMessage);

        // Missing start
        assertParseFailure(parser,
                EVENT_TITLE_DESC + EVENT_END_DESC + EVENT_CONTACT_DESC_1, expectedMessage);

        // Missing end
        assertParseFailure(parser,
                EVENT_TITLE_DESC + EVENT_START_DESC + EVENT_CONTACT_DESC_1, expectedMessage);

        // All missing
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidDateTimeFormat_failure() {
        assertParseFailure(parser,
                EVENT_TITLE_DESC + INVALID_EVENT_START_DESC + EVENT_END_DESC + EVENT_CONTACT_DESC_1,
                ParserUtil.MESSAGE_INVALID_DATE_TIME);

        assertParseFailure(parser,
                EVENT_TITLE_DESC + EVENT_START_DESC + INVALID_EVENT_END_DESC + EVENT_CONTACT_DESC_1,
                ParserUtil.MESSAGE_INVALID_DATE_TIME);
    }

    @Test
    public void parse_endBeforeStart_failure() {
        String invalidEndDesc = " " + CliSyntax.PREFIX_EVENT_END + "2025-04-01 13:00";

        assertParseFailure(parser,
                EVENT_TITLE_DESC + EVENT_START_DESC + invalidEndDesc + EVENT_CONTACT_DESC_1,
                MESSAGE_END_BEFORE_START);
    }

    @Test
    public void parse_invalidContactIndex_failure() {
        assertParseFailure(parser,
                EVENT_TITLE_DESC + EVENT_START_DESC + EVENT_END_DESC + INVALID_EVENT_CONTACT_DESC,
                ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_duplicatePrefix_failure() {
        // duplicate -t
        assertParseFailure(parser,
                " -t Alpha -t Beta" + EVENT_START_DESC + EVENT_END_DESC + EVENT_CONTACT_DESC_1,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_EVENT_TITLE));

        // duplicate -s
        assertParseFailure(parser,
                EVENT_TITLE_DESC + " -s 2025-04-01 12:00 -s 2025-04-01 13:00" + EVENT_END_DESC + EVENT_CONTACT_DESC_1,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_EVENT_START));

        // duplicate -e
        assertParseFailure(parser,
                EVENT_TITLE_DESC + EVENT_START_DESC + " -e 2025-04-01 13:00 -e 2025-04-01 14:00" + EVENT_CONTACT_DESC_1,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_EVENT_END));
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        assertParseFailure(parser,
                PREAMBLE_NON_EMPTY + EVENT_TITLE_DESC + EVENT_START_DESC + EVENT_END_DESC + EVENT_CONTACT_DESC_1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }
}
