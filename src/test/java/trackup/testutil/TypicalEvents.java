package trackup.testutil;

import static trackup.testutil.TypicalPersons.ALICE;
import static trackup.testutil.TypicalPersons.BOB;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import trackup.model.AddressBook;
import trackup.model.event.Event;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    public static final Event MEETING_EVENT = new EventBuilder()
            .withTitle("Team Meeting")
            .withStart(LocalDateTime.of(2025, 4, 1, 14, 0))
            .withEnd(LocalDateTime.of(2025, 4, 1, 15, 0))
            .addContacts(ALICE)
            .build();

    public static final Event LUNCH_EVENT = new EventBuilder()
            .withTitle("Lunch with Bob")
            .withStart(LocalDateTime.of(2025, 4, 2, 12, 0))
            .withEnd(LocalDateTime.of(2025, 4, 2, 13, 0))
            .addContacts(BOB)
            .build();

    public static final Event SOLO_EVENT = new EventBuilder()
            .withTitle("Solo Planning")
            .withStart(LocalDateTime.of(2025, 4, 3, 9, 0))
            .withEnd(LocalDateTime.of(2025, 4, 3, 10, 0))
            .withContacts(new HashSet<>()) // no contacts
            .build();

    public static final Event DUPLICATE_MEETING = new EventBuilder(MEETING_EVENT).build(); // same as MEETING_EVENT

    /**
     * Returns an {@code AddressBook} with the typical events and persons.
     */
    public static AddressBook getTypicalAddressBookWithEvents() {
        AddressBook ab = new AddressBook();
        ab.addPerson(ALICE);
        ab.addPerson(BOB);
        ab.addEvent(MEETING_EVENT);
        ab.addEvent(LUNCH_EVENT);
        ab.addEvent(SOLO_EVENT);
        return ab;
    }

    public static List<Event> getTypicalEvents() {
        return Arrays.asList(MEETING_EVENT, LUNCH_EVENT, SOLO_EVENT);
    }
}

