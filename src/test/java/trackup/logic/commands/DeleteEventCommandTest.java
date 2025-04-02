package trackup.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
        Set<Index> indexSet = Set.of(INDEX_FIRST_PERSON);
        Set<Index> differentIndexSet = Set.of(INDEX_SECOND_PERSON);
        LocalDateTime start = LocalDateTime.of(2025, 4, 1, 14, 0);
        LocalDateTime end = LocalDateTime.of(2025, 4, 1, 15, 0);

        DeleteEventCommand base = new DeleteEventCommand("Meeting", start, end, indexSet);

        // Different title
        assertFalse(base.equals(new DeleteEventCommand("Different", start, end, indexSet)));

        // Different start time
        assertFalse(base.equals(new DeleteEventCommand("Meeting",
                LocalDateTime.of(2025, 4, 2, 14, 0), end, indexSet)));

        // Different end time
        assertFalse(base.equals(new DeleteEventCommand("Meeting", start,
                LocalDateTime.of(2025, 4, 2, 15, 0), indexSet)));

        // Different contact set
        assertFalse(base.equals(new DeleteEventCommand("Meeting", start, end, differentIndexSet)));

        // Null title, others same
        assertFalse(base.equals(new DeleteEventCommand(null, start, end, indexSet)));

        // Null start, others same
        assertFalse(base.equals(new DeleteEventCommand("Meeting", null, end, indexSet)));

        // Null end, others same
        assertFalse(base.equals(new DeleteEventCommand("Meeting", start, null, indexSet)));

        // All null except contacts (same)
        DeleteEventCommand onlyContacts = new DeleteEventCommand(null, null, null, indexSet);
        assertFalse(base.equals(onlyContacts));
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
