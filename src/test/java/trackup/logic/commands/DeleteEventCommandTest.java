package trackup.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static trackup.logic.commands.CommandTestUtil.assertCommandFailure;
import static trackup.logic.commands.CommandTestUtil.assertCommandSuccess;
import static trackup.testutil.TypicalEvents.LUNCH_EVENT;
import static trackup.testutil.TypicalEvents.MEETING_EVENT;
import static trackup.testutil.TypicalEvents.getTypicalAddressBookWithEvents;
import static trackup.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static trackup.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import trackup.commons.core.index.Index;
import trackup.commons.util.ToStringBuilder;
import trackup.logic.Messages;
import trackup.model.Model;
import trackup.model.ModelManager;
import trackup.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteEventCommand}.
 */
public class DeleteEventCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBookWithEvents(), new UserPrefs());
    }

    @Test
    public void execute_matchByTitle_success() {
        DeleteEventCommand command = new DeleteEventCommand("Meeting", null, null, Collections.emptySet());

        Model expectedModel = new ModelManager(getTypicalAddressBookWithEvents(), new UserPrefs());
        expectedModel.deleteEvent(MEETING_EVENT);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_SUCCESS, Messages.format(MEETING_EVENT));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_matchByTime_success() {
        DeleteEventCommand command = new DeleteEventCommand(
                null, MEETING_EVENT.getStartDateTime(), MEETING_EVENT.getEndDateTime(), Collections.emptySet());

        Model expectedModel = new ModelManager(getTypicalAddressBookWithEvents(), new UserPrefs());
        expectedModel.deleteEvent(MEETING_EVENT);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_SUCCESS, Messages.format(MEETING_EVENT));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_matchByContact_success() {
        Set<Index> contactIndexes = Set.of(INDEX_FIRST_PERSON); // ALICE is index 0

        DeleteEventCommand command = new DeleteEventCommand(null, null, null, contactIndexes);

        Model expectedModel = new ModelManager(getTypicalAddressBookWithEvents(), new UserPrefs());
        expectedModel.deleteEvent(MEETING_EVENT);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_SUCCESS, Messages.format(MEETING_EVENT));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_matchByAllFilters_success() {
        DeleteEventCommand command = new DeleteEventCommand(
                "Lunch", LUNCH_EVENT.getStartDateTime(), LUNCH_EVENT.getEndDateTime(), Set.of(INDEX_SECOND_PERSON));

        Model expectedModel = new ModelManager(getTypicalAddressBookWithEvents(), new UserPrefs());
        expectedModel.deleteEvent(LUNCH_EVENT);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_SUCCESS, Messages.format(LUNCH_EVENT));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noMatch_throwsCommandException() {
        DeleteEventCommand command = new DeleteEventCommand("DoesNotExist", null, null, Collections.emptySet());

        assertCommandFailure(command, model, DeleteEventCommand.MESSAGE_EVENT_NOT_FOUND);
    }

    @Test
    public void execute_partialMatchWrongTime_throwsCommandException() {
        DeleteEventCommand command = new DeleteEventCommand(
                "Meeting",
                LocalDateTime.of(2025, 4, 1, 10, 0),
                LocalDateTime.of(2025, 4, 1, 11, 0),
                Collections.emptySet());

        assertCommandFailure(command, model, DeleteEventCommand.MESSAGE_EVENT_NOT_FOUND);
    }

    @Test
    public void equals() {
        Set<Index> indexSetA = Set.of(INDEX_FIRST_PERSON);
        Set<Index> indexSetB = Set.of(INDEX_SECOND_PERSON);
        LocalDateTime startA = LocalDateTime.of(2025, 4, 1, 14, 0);
        LocalDateTime startB = LocalDateTime.of(2025, 5, 1, 14, 0);
        LocalDateTime endA = LocalDateTime.of(2025, 4, 1, 15, 0);
        LocalDateTime endB = LocalDateTime.of(2025, 5, 1, 15, 0);

        DeleteEventCommand allFieldsCommand = new DeleteEventCommand("Meeting", startA, endA, indexSetA);

        // same object -> returns true
        assertTrue(allFieldsCommand.equals(allFieldsCommand));

        // not instanceof -> returns false
        assertFalse(allFieldsCommand.equals("not a command"));

        // null -> returns false
        assertFalse(allFieldsCommand.equals(null));

        // Same fields = should be equal
        assertTrue(allFieldsCommand.equals(new DeleteEventCommand("Meeting", startA, endA, indexSetA)));

        // partialTitle null
        DeleteEventCommand nullTitleCommandA = new DeleteEventCommand(null, startA, endA, indexSetA);
        DeleteEventCommand nullTitleCommandB = new DeleteEventCommand(null, startA, endA, indexSetA);
        assertTrue(nullTitleCommandA.equals(nullTitleCommandB));

        // partialTitle not null
        DeleteEventCommand notNullTitleCommandA = new DeleteEventCommand("Meeting", startA, endA, indexSetA);
        DeleteEventCommand notNullTitleCommandB = new DeleteEventCommand("Follow-up", startA, endA, indexSetA);
        assertFalse(notNullTitleCommandA.equals(notNullTitleCommandB));

        // startDateTime null
        DeleteEventCommand nullStartCommandA = new DeleteEventCommand("Meeting", null, endA, indexSetA);
        DeleteEventCommand nullStartCommandB = new DeleteEventCommand("Meeting", null, endA, indexSetA);
        assertTrue(nullStartCommandA.equals(nullStartCommandB));

        // startDateTime not null
        DeleteEventCommand notNullStartCommandA = new DeleteEventCommand("Meeting", startA, endA, indexSetA);
        DeleteEventCommand notNullStartCommandB = new DeleteEventCommand("Meeting", startB, endA, indexSetA);
        assertFalse(notNullStartCommandA.equals(notNullStartCommandB));

        // endDateTime null
        DeleteEventCommand nullEndCommandA = new DeleteEventCommand("Meeting", startA, null, indexSetA);
        DeleteEventCommand nullEndCommandB = new DeleteEventCommand("Meeting", startA, null, indexSetA);
        assertTrue(nullEndCommandA.equals(nullEndCommandB));

        // endDateTime not null
        DeleteEventCommand notNullEndCommandA = new DeleteEventCommand("Meeting", startA, endA, indexSetA);
        DeleteEventCommand notNullEndCommandB = new DeleteEventCommand("Meeting", startA, endB, indexSetA);
        assertFalse(notNullEndCommandA.equals(notNullEndCommandB));

        // contactIndexes empty
        DeleteEventCommand emptyContactCommandA = new DeleteEventCommand("Meeting", startA, endA, Set.of());
        DeleteEventCommand emptyContactCommandB = new DeleteEventCommand("Meeting", startA, endB, Set.of());
        assertTrue(emptyContactCommandA.equals(emptyContactCommandB));

        // contactIndexes not empty
        DeleteEventCommand notEmptyContactCommandA = new DeleteEventCommand("Meeting", startA, endA, indexSetA);
        DeleteEventCommand notEmptyContactCommandB = new DeleteEventCommand("Meeting", startA, endB, indexSetB);
        assertTrue(notEmptyContactCommandA.equals(notEmptyContactCommandB));

        // All but contactIndexes are null
        assertFalse(allFieldsCommand.equals(new DeleteEventCommand(null, null, null, indexSetA)));

        // All fields null and empty set
        DeleteEventCommand emptyCommandA = new DeleteEventCommand(null, null, null, Set.of());
        DeleteEventCommand emptyCommandB = new DeleteEventCommand(null, null, null, Set.of());
        assertTrue(emptyCommandA.equals(emptyCommandB));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        DeleteEventCommand command = new DeleteEventCommand("Meeting",
                LocalDateTime.of(2025, 4, 1, 14, 0),
                LocalDateTime.of(2025, 4, 1, 15, 0),
                Set.of(index));

        String expected = new ToStringBuilder(command)
                .add("partialTitle", "Meeting")
                .add("startDateTime", LocalDateTime.of(2025, 4, 1, 14, 0))
                .add("endDateTime", LocalDateTime.of(2025, 4, 1, 15, 0))
                .add("contactIndexes", Set.of(index))
                .toString();

        assertEquals(expected, command.toString());
    }

}
