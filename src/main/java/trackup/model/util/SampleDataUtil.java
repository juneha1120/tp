package trackup.model.util;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import trackup.model.AddressBook;
import trackup.model.ReadOnlyAddressBook;
import trackup.model.category.Category;
import trackup.model.event.Event;
import trackup.model.person.Address;
import trackup.model.person.Email;
import trackup.model.person.Name;
import trackup.model.person.Person;
import trackup.model.person.Phone;
import trackup.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends"), Optional.of(new Category("Client"))),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends"), Optional.of(new Category("Investor"))),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours"), Optional.of(new Category("Partner"))),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family"), Optional.of(new Category("Other"))),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates"), Optional.of(new Category("Client"))),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"), Optional.of(new Category("Investor")))
        };
    }

    public static Event[] getSampleEvents() {
        Person[] samplePersons = getSamplePersons();

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime thisTuesday = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.TUESDAY));
        LocalDateTime thisThursday = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.THURSDAY));
        LocalDateTime thisSaturday = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));

        return new Event[] {
            new Event("Follow-Ups",
                thisTuesday.with(LocalTime.of(6, 0)),
                thisTuesday.with(LocalTime.of(8, 0)),
                new HashSet<>(Set.of(samplePersons[0], samplePersons[1]))),
            new Event("Contract Negotiations",
                thisThursday.with(LocalTime.of(1, 0)),
                thisThursday.with(LocalTime.of(3, 0)),
                new HashSet<>(Set.of(samplePersons[2], samplePersons[3]))),
            new Event("Quarterly Reports",
                thisSaturday.with(LocalTime.of(13, 0)),
                thisSaturday.with(LocalTime.of(15, 0)),
                new HashSet<>(Set.of(samplePersons[4], samplePersons[5])))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        for (Event sampleEvent : getSampleEvents()) {
            sampleAb.addEvent(sampleEvent);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
