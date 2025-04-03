package trackup.testutil;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import trackup.model.event.Event;
import trackup.model.person.Person;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_TITLE = "Team Meeting";
    public static final LocalDateTime DEFAULT_START = LocalDateTime.of(2025, 4, 1, 14, 0);
    public static final LocalDateTime DEFAULT_END = LocalDateTime.of(2025, 4, 1, 15, 0);

    private String title;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Set<Person> contacts;

    /**
     * Creates an {@code EventBuilder} with the default details.
     */
    public EventBuilder() {
        title = DEFAULT_TITLE;
        startDateTime = DEFAULT_START;
        endDateTime = DEFAULT_END;
        contacts = new HashSet<>();
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        title = eventToCopy.getTitle();
        startDateTime = eventToCopy.getStartDateTime();
        endDateTime = eventToCopy.getEndDateTime();
        contacts = new HashSet<>(eventToCopy.getContacts());
    }

    /**
     * Sets the {@code title} of the {@code Event} that we are building.
     */
    public EventBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Sets the {@code startDateTime} of the {@code Event} that we are building.
     */
    public EventBuilder withStart(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
        return this;
    }

    /**
     * Sets the {@code endDateTime} of the {@code Event} that we are building.
     */
    public EventBuilder withEnd(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
        return this;
    }

    /**
     * Sets the contacts of the {@code Event} that we are building.
     */
    public EventBuilder withContacts(Set<Person> contacts) {
        this.contacts = new HashSet<>(contacts);
        return this;
    }

    /**
     * Adds one or more {@code Person} to the event's contacts.
     */
    public EventBuilder addContacts(Person... persons) {
        for (Person p : persons) {
            contacts.add(p);
        }
        return this;
    }

    public Event build() {
        return new Event(title, startDateTime, endDateTime, contacts);
    }
}
