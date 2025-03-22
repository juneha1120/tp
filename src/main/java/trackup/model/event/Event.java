package trackup.model.event;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import trackup.model.person.Person;

/**
 * Represents an Event in the calendar.
 * An Event has a title, a start time, an end time, and a set of linked contacts.
 * Events are immutable once created.
 */
public class Event {
    private final String title;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final Set<Person> contacts;

    /**
     * Constructs an {@code Event}.
     *
     * @param title The title of the event.
     * @param startDateTime The starting date and time of the event.
     * @param endDateTime The ending date and time of the event.
     * @param contacts A set of contacts linked to this event.
     */
    public Event(String title, LocalDateTime startDateTime, LocalDateTime endDateTime, Set<Person> contacts) {
        this.title = title;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.contacts = new HashSet<>(contacts);
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    /**
     * Returns an unmodifiable view of the contacts linked to this event.
     *
     * @return A set of contacts.
     */
    public Set<Person> getContacts() {
        return Collections.unmodifiableSet(contacts);
    }

    @Override
    public String toString() {
        return title + " (" + startDateTime + " to " + endDateTime + ") with " + contacts;
    }
}
