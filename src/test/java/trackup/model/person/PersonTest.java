package trackup.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static trackup.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static trackup.logic.commands.CommandTestUtil.VALID_CATEGORY_INVESTOR;
import static trackup.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static trackup.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static trackup.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static trackup.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static trackup.testutil.Assert.assertThrows;
import static trackup.testutil.TypicalPersons.ALICE;
import static trackup.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import trackup.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name but different phone/email -> returns false per new duplicate rules
        Person editedAlice = new PersonBuilder(ALICE)
                .withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSamePerson(editedBob));
    }

    @Test
    public void isSamePerson_sameObject_returnsTrue() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));
    }

    @Test
    public void isSamePerson_null_returnsFalse() {
        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));
    }

    @Test
    public void isSamePerson_allFieldsSame_returnsTrue() {
        // name, phone, email all match
        Person aliceCopy = new PersonBuilder(ALICE).build(); // same as ALICE
        assertTrue(ALICE.isSamePerson(aliceCopy));
    }

    @Test
    public void isSamePerson_sameNameDifferentPhone_returnsFalse() {
        // phone differs
        Person aliceDifferentPhone = new PersonBuilder(ALICE).withPhone("87654321").build();
        assertFalse(ALICE.isSamePerson(aliceDifferentPhone));
    }

    @Test
    public void isSamePerson_sameNameDifferentEmail_returnsFalse() {
        // email differs
        Person aliceDifferentEmail = new PersonBuilder(ALICE).withEmail("diff@example.com").build();
        assertFalse(ALICE.isSamePerson(aliceDifferentEmail));
    }

    @Test
    public void isSamePerson_differentNameSamePhoneSameEmail_returnsFalse() {
        // name differs
        Person differentName = new PersonBuilder(ALICE).withName("Alice B").build();
        assertFalse(ALICE.isSamePerson(differentName));
    }



    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));

        // different category -> returns false
        editedAlice = new PersonBuilder(ALICE).withCategory(VALID_CATEGORY_INVESTOR).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress() + ", tags=" + ALICE.getTags()
                + ", category=" + ALICE.getCategory().orElse(null)
                + ", notes=" + ALICE.getNotes() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
