package trackup.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static trackup.storage.JsonAdaptedEvent.MISSING_FIELD_MESSAGE_FORMAT;
import static trackup.testutil.Assert.assertThrows;
import static trackup.testutil.TypicalEvents.MEETING_EVENT;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import trackup.commons.exceptions.IllegalValueException;

public class JsonAdaptedEventTest {
    private static final String INVALID_START = "not-a-date";
    private static final String INVALID_END = "another-bad-date";

    private static final String VALID_TITLE = MEETING_EVENT.getTitle();
    private static final String VALID_START = MEETING_EVENT.getStartDateTime().toString();
    private static final String VALID_END = MEETING_EVENT.getEndDateTime().toString();
    private static final List<JsonAdaptedPerson> VALID_CONTACTS = MEETING_EVENT.getContacts().stream()
            .map(JsonAdaptedPerson::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validEvent_returnsEvent() throws Exception {
        JsonAdaptedEvent event = new JsonAdaptedEvent(MEETING_EVENT);
        assertEquals(MEETING_EVENT, event.toModelType());
    }

    @Test
    public void toModelType_nullTitle_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(null, VALID_START, VALID_END, VALID_CONTACTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Title");
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullStart_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_TITLE, null, VALID_END, VALID_CONTACTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Start DateTime");
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullEnd_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_TITLE, VALID_START, null, VALID_CONTACTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "End DateTime");
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidStart_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_TITLE, INVALID_START, VALID_END, VALID_CONTACTS);
        assertThrows(IllegalValueException.class, "Invalid DateTime format for Event.", event::toModelType);
    }

    @Test
    public void toModelType_invalidEnd_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_TITLE, VALID_START, INVALID_END, VALID_CONTACTS);
        assertThrows(IllegalValueException.class, "Invalid DateTime format for Event.", event::toModelType);
    }

    @Test
    public void toModelType_invalidContact_throwsIllegalValueException() {
        List<JsonAdaptedPerson> invalidContacts = List.of(new JsonAdaptedPerson(
                null, "12345678", "email@example.com", "Some Street", List.of(), null, List.of()));
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_TITLE, VALID_START, VALID_END, invalidContacts);
        assertThrows(IllegalValueException.class, event::toModelType);
    }
}

