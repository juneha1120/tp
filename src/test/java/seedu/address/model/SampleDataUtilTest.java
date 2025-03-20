package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.model.util.SampleDataUtil;

public class SampleDataUtilTest {

    @Test
    public void getSamplePersons_returnsCorrectType() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();

        assertNotNull(samplePersons); // Ensure the array is not null
        assertTrue(samplePersons.length > 0); // Ensure there are sample persons
        assertTrue(samplePersons instanceof Person[]); // Check if it's a Person array
        assertTrue(samplePersons[0] instanceof Person); // Ensure first element is a Person object
    }

    @Test
    public void getSampleAddressBook_returnsCorrectType() {
        ReadOnlyAddressBook sampleAddressBook = SampleDataUtil.getSampleAddressBook();

        assertNotNull(sampleAddressBook); // Ensure the address book is not null
        assertTrue(sampleAddressBook instanceof ReadOnlyAddressBook); // Check if it's ReadOnlyAddressBook
        assertTrue(sampleAddressBook.getPersonList().size() > 0); // Ensure the address book contains persons
    }
}
