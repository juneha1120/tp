package trackup.storage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import trackup.commons.exceptions.IllegalValueException;
import trackup.model.event.Event;
import trackup.model.person.Person;

/**
 * Jackson-friendly version of {@link Event}.
 */
class JsonAdaptedEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    private final String title;
    private final String startDateTime;
    private final String endDateTime;
    private final List<JsonAdaptedPerson> contacts = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given event details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("title") String title,
                            @JsonProperty("startDateTime") String startDateTime,
                            @JsonProperty("endDateTime") String endDateTime,
                            @JsonProperty("contacts") List<JsonAdaptedPerson> contacts) {
        this.title = title;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        if (contacts != null) {
            this.contacts.addAll(contacts);
        }
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        title = source.getTitle();
        startDateTime = source.getStartDateTime().toString();
        endDateTime = source.getEndDateTime().toString();
        contacts.addAll(source.getContacts().stream()
                .map(JsonAdaptedPerson::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event.
     */
    public Event toModelType() throws IllegalValueException {
        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Title"));
        }
        if (startDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Start DateTime"));
        }
        if (endDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "End DateTime"));
        }

        LocalDateTime modelStartDateTime;
        LocalDateTime modelEndDateTime;
        try {
            modelStartDateTime = LocalDateTime.parse(startDateTime);
            modelEndDateTime = LocalDateTime.parse(endDateTime);
        } catch (Exception e) {
            throw new IllegalValueException("Invalid DateTime format for Event.");
        }

        Set<Person> eventContacts = new HashSet<>();
        for (JsonAdaptedPerson contact : contacts) {
            eventContacts.add(contact.toModelType());
        }

        return new Event(title, modelStartDateTime, modelEndDateTime, eventContacts);
    }
}
