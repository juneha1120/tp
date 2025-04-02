package trackup.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static trackup.testutil.Assert.assertThrows;
import static trackup.testutil.TypicalPersons.ALICE;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import trackup.commons.util.ToStringBuilder;
import trackup.logic.Messages;
import trackup.logic.commands.exceptions.CommandException;
import trackup.model.Model;
import trackup.model.event.Event;
import trackup.model.person.Address;
import trackup.model.person.Email;
import trackup.model.person.Name;
import trackup.model.person.Person;
import trackup.model.person.Phone;
import trackup.model.tag.Tag;
import trackup.testutil.PersonBuilder;

public class DeleteByCommandTest {

    private static final String TITLE_MEETING = "Team Meeting";
    private static final LocalDateTime START = LocalDateTime.of(2024, 5, 1, 10, 0);
    private static final LocalDateTime END = LocalDateTime.of(2024, 5, 1, 12, 0);
    private static final LocalDateTime DIFFERENT_START = LocalDateTime.of(2024, 5, 2, 10, 0);
    private static final LocalDateTime DIFFERENT_END = LocalDateTime.of(2024, 5, 2, 12, 0);

    @Mock
    private Model model;

    @Mock
    private Event event;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void constructor_nullCriteria_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new DeleteByCommand(null, null, null, null, null));
    }

    @Test
    public void execute_personFound_success() throws Exception {
        when(model.getFilteredPersonList()).thenReturn(FXCollections.observableArrayList(ALICE));
        when(model.hasPerson(ALICE)).thenReturn(true);
        List<Event> eventList = List.of();
        ObservableList<Event> observableEventList = FXCollections.observableArrayList(eventList);
        when(model.getEventList()).thenReturn(observableEventList);

        DeleteByCommand deleteCommand = new DeleteByCommand(
                Optional.of(ALICE.getName()), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty());

        CommandResult commandResult = deleteCommand.execute(model);

        verify(model, times(1)).deletePerson(ALICE);
        assertEquals(String.format(DeleteByCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(ALICE)), commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_deleteByPhone_success() throws Exception {
        when(model.getFilteredPersonList()).thenReturn(FXCollections.observableArrayList(ALICE));
        when(model.hasPerson(ALICE)).thenReturn(true);
        List<Event> eventList = List.of();
        ObservableList<Event> observableEventList = FXCollections.observableArrayList(eventList);
        when(model.getEventList()).thenReturn(observableEventList);

        DeleteByCommand deleteCommand = new DeleteByCommand(
                Optional.empty(), Optional.of(ALICE.getPhone()), Optional.empty(),
                Optional.empty(), Optional.empty());

        CommandResult commandResult = deleteCommand.execute(model);

        verify(model, times(1)).deletePerson(ALICE);
        assertEquals(String.format(DeleteByCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(ALICE)), commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_deleteByEmail_success() throws Exception {
        when(model.getFilteredPersonList()).thenReturn(FXCollections.observableArrayList(ALICE));
        when(model.hasPerson(ALICE)).thenReturn(true);
        List<Event> eventList = List.of();
        ObservableList<Event> observableEventList = FXCollections.observableArrayList(eventList);
        when(model.getEventList()).thenReturn(observableEventList);

        DeleteByCommand deleteCommand = new DeleteByCommand(
                Optional.empty(), Optional.empty(), Optional.of(ALICE.getEmail()),
                Optional.empty(), Optional.empty());

        CommandResult commandResult = deleteCommand.execute(model);

        verify(model, times(1)).deletePerson(ALICE);
        assertEquals(String.format(DeleteByCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(ALICE)), commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_deleteByAddress_success() throws Exception {
        when(model.getFilteredPersonList()).thenReturn(FXCollections.observableArrayList(ALICE));
        when(model.hasPerson(ALICE)).thenReturn(true);
        when(event.getContacts()).thenReturn(Set.of(ALICE));
        when(event.getTitle()).thenReturn(TITLE_MEETING);
        when(event.getStartDateTime()).thenReturn(START);
        when(event.getEndDateTime()).thenReturn(END);
        when(event.getContacts()).thenReturn(Set.of(ALICE));
        List<Event> eventList = List.of(event);
        ObservableList<Event> observableEventList = FXCollections.observableArrayList(eventList);
        when(model.getEventList()).thenReturn(observableEventList);

        DeleteByCommand deleteCommand = new DeleteByCommand(
                Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.of(ALICE.getAddress()), Optional.empty());

        CommandResult commandResult = deleteCommand.execute(model);

        verify(model, times(1)).deletePerson(ALICE);
        assertEquals(String.format(DeleteByCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(ALICE)), commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_deleteByTag_success() throws Exception {
        Tag tag = ALICE.getTags().iterator().next();
        when(model.getFilteredPersonList()).thenReturn(FXCollections.observableArrayList(ALICE));
        when(model.hasPerson(ALICE)).thenReturn(true);
        List<Event> eventList = List.of();
        ObservableList<Event> observableEventList = FXCollections.observableArrayList(eventList);
        when(model.getEventList()).thenReturn(observableEventList);

        DeleteByCommand deleteCommand = new DeleteByCommand(
                Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.of(tag));

        CommandResult commandResult = deleteCommand.execute(model);

        verify(model, times(1)).deletePerson(ALICE);
        assertEquals(String.format(DeleteByCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(ALICE)), commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_personNotFound_throwsCommandException() {
        when(model.getFilteredPersonList()).thenReturn(FXCollections.observableArrayList());

        DeleteByCommand deleteCommand = new DeleteByCommand(
                Optional.of(new Name("Nonexistent Name")), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty());

        assertThrows(CommandException.class, () -> deleteCommand.execute(model));
    }

    @Test
    public void execute_multiplePersonsFound_multipleMatchesMessage() throws CommandException {
        Person person1 = new PersonBuilder().withName("John Doe").build();
        Person person2 = new PersonBuilder().withName("John Doe").build();
        when(model.getFilteredPersonList()).thenReturn(FXCollections.observableArrayList(person1, person2));

        DeleteByCommand deleteCommand = new DeleteByCommand(
                Optional.of(new Name("John Doe")), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty());

        CommandResult commandResult = deleteCommand.execute(model);

        // Match exactly what is returned in the code
        assertEquals(Messages.MESSAGE_MULTIPLE_PEOPLE_TO_DELETE, commandResult.getFeedbackToUser());
    }

    @Test
    public void getPredicate_matchesByName() {
        Name testName = new Name("John Doe");
        DeleteByCommand command = new DeleteByCommand(Optional.of(testName), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty());
        Predicate<Person> predicate = command.getPredicate();
        Person matchingPerson = new Person(testName, new Phone("12345678"), new Email("john@example.com"),
                new Address("Street 1"), Set.of(new Tag("Friend")), Optional.empty());
        Person nonMatchingPerson = new Person(new Name("Jane Doe"), new Phone("87654321"),
                new Email("jane@example.com"), new Address("Street 2"), Set.of(new Tag("Friend")),
                Optional.empty());

        assertTrue(predicate.test(matchingPerson));
        assertFalse(predicate.test(nonMatchingPerson));
    }

    @Test
    public void getPredicate_matchesByPhone() {
        Phone testPhone = new Phone("12345678");
        DeleteByCommand command = new DeleteByCommand(Optional.empty(), Optional.of(testPhone),
                Optional.empty(), Optional.empty(), Optional.empty());
        Predicate<Person> predicate = command.getPredicate();
        Person matchingPerson = new Person(new Name("John Doe"), testPhone, new Email("john@example.com"),
                new Address("Street 1"), Set.of(new Tag("Friend")), Optional.empty());
        Person nonMatchingPerson = new Person(new Name("Jane Doe"), new Phone("87654321"),
                new Email("jane@example.com"), new Address("Street 2"), Set.of(new Tag("Friend")),
                Optional.empty());

        assertTrue(predicate.test(matchingPerson));
        assertFalse(predicate.test(nonMatchingPerson));
    }

    @Test
    public void getPredicate_matchesByAddress() {
        Address testAddress = new Address("Street 1");
        DeleteByCommand command = new DeleteByCommand(Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.of(testAddress), Optional.empty());
        Predicate<Person> predicate = command.getPredicate();
        Person matchingPerson = new Person(new Name("John Doe"), new Phone("12345678"), new Email("john@example.com"),
                testAddress, Set.of(new Tag("Friend")), Optional.empty());
        Person nonMatchingPerson = new Person(new Name("Jane Doe"), new Phone("87654321"),
                new Email("jane@example.com"), new Address("Street 2"), Set.of(new Tag("Friend")),
                Optional.empty());

        assertTrue(predicate.test(matchingPerson));
        assertFalse(predicate.test(nonMatchingPerson));
    }

    @Test
    public void getPredicate_matchesByEmail() {
        Email testEmail = new Email("john@example.com");
        DeleteByCommand command = new DeleteByCommand(Optional.empty(), Optional.empty(),
                Optional.of(testEmail), Optional.empty(), Optional.empty());
        Predicate<Person> predicate = command.getPredicate();
        Person matchingPerson = new Person(new Name("John Doe"), new Phone("12345678"), testEmail,
                new Address("Street 1"), Set.of(new Tag("Friend")), Optional.empty());
        Person nonMatchingPerson = new Person(new Name("Jane Doe"), new Phone("87654321"),
                new Email("jane@example.com"), new Address("Street 2"), Set.of(new Tag("Friend")),
                Optional.empty());

        assertTrue(predicate.test(matchingPerson));
        assertFalse(predicate.test(nonMatchingPerson));
    }

    @Test
    public void getPredicate_matchesByTag() {
        Tag testTag = new Tag("Friend");
        DeleteByCommand command = new DeleteByCommand(Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.of(testTag));
        Predicate<Person> predicate = command.getPredicate();
        Person matchingPerson = new Person(new Name("John Doe"), new Phone("12345678"), new Email("john@example.com"),
                new Address("Street 1"), Set.of(testTag), Optional.empty());
        Person nonMatchingPerson = new Person(new Name("Jane Doe"), new Phone("87654321"),
                new Email("jane@example.com"), new Address("Street 2"), Set.of(new Tag("Colleague")),
                Optional.empty());

        assertTrue(predicate.test(matchingPerson));
        assertFalse(predicate.test(nonMatchingPerson));
    }

    @Test
    public void getPredicate_matchesByMultipleCriteria() {
        Name testName = new Name("John Doe");
        Phone testPhone = new Phone("12345678");
        Email testEmail = new Email("john@example.com");
        DeleteByCommand command = new DeleteByCommand(Optional.of(testName), Optional.of(testPhone),
                Optional.of(testEmail), Optional.empty(), Optional.empty());
        Predicate<Person> predicate = command.getPredicate();
        Set<Tag> testTags = Set.of(new Tag("Friend"));
        Person matchingPerson = new Person(testName, testPhone, testEmail, new Address("Street 1"),
                Set.of(new Tag("Friend")), Optional.empty());
        Person nonMatchingPerson = new Person(new Name("John Doe"), testPhone, new Email("wrong@example.com"),
                new Address("Street 1"), Set.of(new Tag("Friend")), Optional.empty());

        assertTrue(predicate.test(matchingPerson));
        assertFalse(predicate.test(nonMatchingPerson));
    }

    @Test
    public void getPredicate_noCriteriaMatchesAll() {
        DeleteByCommand command = new DeleteByCommand(Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty());
        Predicate<Person> predicate = command.getPredicate();
        Person person = new Person(new Name("John Doe"), new Phone("12345678"), new Email("john@example.com"),
                new Address("Street 1"), Set.of(new Tag("Friend")), Optional.empty());

        assertTrue(predicate.test(person));
    }

    @Test
    public void addCriteriaToStringBuilder_includesCorrectFields() {
        Name testName = new Name("John Doe");
        Phone testPhone = new Phone("12345678");
        Email testEmail = new Email("john@example.com");
        Address testAddress = new Address("Street 1");
        Tag testTag = new Tag("Friend");
        DeleteByCommand command = new DeleteByCommand(Optional.of(testName), Optional.of(testPhone),
                Optional.of(testEmail), Optional.of(testAddress), Optional.of(testTag));

        ToStringBuilder stringBuilder = new ToStringBuilder(command);
        command.addCriteriaToStringBuilder(stringBuilder);
        String result = stringBuilder.toString();

        assertTrue(result.contains("name=John Doe"));
        assertTrue(result.contains("phone=12345678"));
        assertTrue(result.contains("email=john@example.com"));
        assertTrue(result.contains("address=Street 1"));
        assertTrue(result.contains("tag=[Friend]"));
    }

    @Test
    public void addCriteriaToStringBuilder_excludesEmptyFields() {
        DeleteByCommand command = new DeleteByCommand(Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty());

        ToStringBuilder stringBuilder = new ToStringBuilder(command);
        command.addCriteriaToStringBuilder(stringBuilder);
        String result = stringBuilder.toString();

        assertFalse(result.contains("name="));
        assertFalse(result.contains("phone="));
        assertFalse(result.contains("email="));
        assertFalse(result.contains("address="));
        assertFalse(result.contains("tag="));
    }

    @Test
    public void equals() {
        DeleteByCommand deleteByName = new DeleteByCommand(
                Optional.of(new Name("Alice")), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty());
        DeleteByCommand deleteByPhone = new DeleteByCommand(
                Optional.empty(), Optional.of(new Phone("12345678")),
                Optional.empty(), Optional.empty(), Optional.empty());
        DeleteByCommand deleteByEmail = new DeleteByCommand(
                Optional.empty(), Optional.empty(), Optional.of(new Email("alice@example.com")),
                Optional.empty(), Optional.empty());
        DeleteByCommand deleteByAddress = new DeleteByCommand(
                Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.of(new Address("123 Street")), Optional.empty());
        DeleteByCommand deleteByTag = new DeleteByCommand(
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.of(new Tag("Friend")));
        DeleteByCommand deleteByEmpty = new DeleteByCommand(
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty());

        // same object -> returns true
        assertEquals(deleteByName, deleteByName);

        // same values -> returns true
        DeleteByCommand deleteByNameCopy = new DeleteByCommand(
                Optional.of(new Name("Alice")), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty());
        assertEquals(deleteByName, deleteByNameCopy);

        // different types -> returns false
        assertNotEquals(deleteByName, 1);

        // null -> returns false
        assertNotEquals(deleteByName, null);

        // different criteria -> returns false
        assertNotEquals(deleteByName, deleteByPhone);
        assertNotEquals(deleteByPhone, deleteByEmail);
        assertNotEquals(deleteByEmail, deleteByAddress);
        assertNotEquals(deleteByAddress, deleteByTag);
        assertNotEquals(deleteByTag, deleteByEmpty);
    }
}
