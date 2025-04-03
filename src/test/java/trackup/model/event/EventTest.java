package trackup.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static trackup.testutil.Assert.assertThrows;
import static trackup.testutil.TypicalPersons.ALICE;
import static trackup.testutil.TypicalPersons.BOB;

import java.time.LocalDateTime;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class EventTest {

    private static final String TITLE_MEETING = "Team Meeting";
    private static final String TITLE_WORKSHOP = "Workshop";

    private static final LocalDateTime START = LocalDateTime.of(2024, 5, 1, 10, 0);
    private static final LocalDateTime END = LocalDateTime.of(2024, 5, 1, 12, 0);
    private static final LocalDateTime DIFFERENT_START = LocalDateTime.of(2024, 5, 2, 10, 0);
    private static final LocalDateTime DIFFERENT_END = LocalDateTime.of(2024, 5, 2, 12, 0);

    private final Event baseEvent = new Event(TITLE_MEETING, START, END, Set.of(ALICE));

    @Test
    public void getContacts_modifySet_throwsUnsupportedOperationException() {
        Event event = new Event(TITLE_MEETING, START, END, Set.of(ALICE));
        assertThrows(UnsupportedOperationException.class, () -> event.getContacts().remove(ALICE));
    }

    @Test
    public void isSameEvent() {
        // same object -> true
        assertTrue(baseEvent.isSameEvent(baseEvent));

        // null -> false
        assertFalse(baseEvent.isSameEvent(null));

        // same title, start and end -> true
        Event copy = new Event(TITLE_MEETING, START, END, Set.of(BOB));
        assertTrue(baseEvent.isSameEvent(copy));

        // different title -> false
        Event diffTitle = new Event(TITLE_WORKSHOP, START, END, Set.of(ALICE));
        assertFalse(baseEvent.isSameEvent(diffTitle));

        // different start -> false
        Event diffStart = new Event(TITLE_MEETING, DIFFERENT_START, END, Set.of(ALICE));
        assertFalse(baseEvent.isSameEvent(diffStart));

        // different end -> false
        Event diffEnd = new Event(TITLE_MEETING, START, DIFFERENT_END, Set.of(ALICE));
        assertFalse(baseEvent.isSameEvent(diffEnd));
    }

    @Test
    public void equalsMethod() {
        // same values -> true
        Event eventCopy = new Event(TITLE_MEETING, START, END, Set.of(ALICE));
        assertTrue(baseEvent.equals(eventCopy));

        // same object -> true
        assertTrue(baseEvent.equals(baseEvent));

        // null -> false
        assertFalse(baseEvent.equals(null));

        // different type -> false
        assertFalse(baseEvent.equals(5));

        // different title -> false
        Event differentTitle = new Event(TITLE_WORKSHOP, START, END, Set.of(ALICE));
        assertFalse(baseEvent.equals(differentTitle));

        // different start -> false
        Event differentStart = new Event(TITLE_MEETING, DIFFERENT_START, END, Set.of(ALICE));
        assertFalse(baseEvent.equals(differentStart));

        // different end -> false
        Event differentEnd = new Event(TITLE_MEETING, START, DIFFERENT_END, Set.of(ALICE));
        assertFalse(baseEvent.equals(differentEnd));

        // different contacts -> false
        Event differentContacts = new Event(TITLE_MEETING, START, END, Set.of(BOB));
        assertFalse(baseEvent.equals(differentContacts));
    }

    @Test
    public void toStringMethod() {
        String expected = "Event{title='Team Meeting', startDateTime=2024-05-01T10:00, "
                + "endDateTime=2024-05-01T12:00, contacts=" + baseEvent.getContacts() + "}";
        assertEquals(expected, baseEvent.toString());
    }
}

