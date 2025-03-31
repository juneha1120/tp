package trackup.model.event;

import static trackup.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
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
    private final Set<Person> contacts = new HashSet<>();

    /**
     * Constructs an {@code Event}.
     *
     * @param title The title of the event.
     * @param startDateTime The starting date and time of the event.
     * @param endDateTime The ending date and time of the event.
     * @param contacts A set of contacts linked to this event.
     */
    public Event(String title, LocalDateTime startDateTime, LocalDateTime endDateTime, Set<Person> contacts) {
        requireAllNonNull(title, startDateTime, endDateTime, contacts);
        this.title = title;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.contacts.addAll(contacts);
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

    /**
     * Returns true if both events have the same title and date/time range.
     * Defines a weaker notion of equality between two events.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }
        return otherEvent != null
                && otherEvent.getTitle().equals(getTitle())
                && otherEvent.getStartDateTime().equals(getStartDateTime())
                && otherEvent.getEndDateTime().equals(getEndDateTime());
    }

    /**
     * Returns true if both events have the same identity and data fields.
     * Defines a stronger notion of equality between two events.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return title.equals(otherEvent.title)
                && startDateTime.equals(otherEvent.startDateTime)
                && endDateTime.equals(otherEvent.endDateTime)
                && contacts.equals(otherEvent.contacts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, startDateTime, endDateTime, contacts);
    }

    @Override
    public String toString() {
        return "Event{" + "title='" + title + '\'' + ", startDateTime=" + startDateTime
                + ", endDateTime=" + endDateTime + ", contacts=" + contacts + '}';
    }
}
