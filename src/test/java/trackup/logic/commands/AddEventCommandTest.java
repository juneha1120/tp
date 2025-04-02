package trackup.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static trackup.logic.commands.AddEventCommand.MESSAGE_DUPLICATE_EVENT;
import static trackup.logic.commands.AddEventCommand.MESSAGE_SUCCESS;
import static trackup.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import trackup.commons.core.index.Index;
import trackup.logic.Messages;
import trackup.logic.commands.exceptions.CommandException;
import trackup.model.event.Event;
import trackup.model.person.Person;
import trackup.testutil.PersonBuilder;

public class AddEventCommandTest {

    private static final String TITLE = "Test Event";
    private static final LocalDateTime START = LocalDateTime.of(2025, 4, 2, 14, 0);
    private static final LocalDateTime END = LocalDateTime.of(2025, 4, 2, 15, 0);

    @Test
    public void constructor_nullFields_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new AddEventCommand(null, START, END, new HashSet<>()));
        assertThrows(NullPointerException.class, () ->
                new AddEventCommand(TITLE, null, END, new HashSet<>()));
        assertThrows(NullPointerException.class, () ->
                new AddEventCommand(TITLE, START, null, new HashSet<>()));
    }

    @Test
    public void execute_eventAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();
        Person contact = new PersonBuilder().build();
        modelStub.contacts.add(contact);

        Set<Index> contactIndexes = Set.of(Index.fromZeroBased(0));
        AddEventCommand command = new AddEventCommand(TITLE, START, END, contactIndexes);

        CommandResult result = command.execute(modelStub);
        Event expectedEvent = new Event(TITLE, START, END, Set.of(contact));

        assertEquals(String.format(MESSAGE_SUCCESS, Messages.format(expectedEvent)),
                result.getFeedbackToUser());
        assertTrue(modelStub.eventsAdded.contains(expectedEvent));
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() {
        Person contact = new PersonBuilder().build();
        Event duplicate = new Event(TITLE, START, END, Set.of(contact));

        AddCommandTest.ModelStub modelStub = new ModelStubWithEvent(duplicate);
        modelStub.contacts.add(contact);

        Set<Index> contactIndexes = Set.of(Index.fromZeroBased(0));
        AddEventCommand command = new AddEventCommand(TITLE, START, END, contactIndexes);

        assertThrows(CommandException.class, MESSAGE_DUPLICATE_EVENT, () -> command.execute(modelStub));
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded(); // no contacts
        Set<Index> contactIndexes = Set.of(Index.fromZeroBased(5)); // out of bounds

        AddEventCommand command = new AddEventCommand(TITLE, START, END, contactIndexes);
        assertThrows(CommandException.class,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, () -> command.execute(modelStub));
    }

    @Test
    public void equals() {
        Set<Index> contacts1 = Set.of(Index.fromOneBased(1));
        Set<Index> contacts2 = Set.of(Index.fromOneBased(2));

        AddEventCommand command1 = new AddEventCommand("Event 1", START, END, contacts1);
        AddEventCommand command2 = new AddEventCommand("Event 1", START, END, contacts1);
        AddEventCommand command3 = new AddEventCommand("Event 2", START, END, contacts1);
        AddEventCommand command4 = new AddEventCommand("Event 1", START, END, contacts2);

        assertTrue(command1.equals(command1)); // same object
        assertTrue(command1.equals(command2)); // same values
        assertFalse(command1.equals(null)); // null
        assertFalse(command1.equals("string")); // different type
        assertFalse(command1.equals(command3)); // different title
        assertFalse(command1.equals(command4)); // different contacts
    }

    @Test
    public void toStringMethod() {
        Set<Index> contactIndexes = Set.of(Index.fromZeroBased(0));
        AddEventCommand command = new AddEventCommand(TITLE, START, END, contactIndexes);
        String expected = AddEventCommand.class.getCanonicalName()
                + "{eventName=" + TITLE
                + ", startDateTime=" + START
                + ", endDateTime=" + END
                + ", contactIndexes=" + contactIndexes + "}";
        assertEquals(expected, command.toString());
    }

    /**
     * Model stub with a single event.
     */
    private static class ModelStubWithEvent extends ModelStubAcceptingEventAdded {
        private final Event event;

        ModelStubWithEvent(Event event) {
            requireNonNull(event);
            this.event = event;
            this.eventsAdded.add(event);
            this.contacts.addAll(event.getContacts());
        }

        @Override
        public boolean hasEvent(Event event) {
            return this.event.isSameEvent(event);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return FXCollections.observableList(contacts);
        }
    }

    /**
     * Model stub that accepts any added event.
     */
    private static class ModelStubAcceptingEventAdded extends AddCommandTest.ModelStub {
        final List<Event> eventsAdded = new ArrayList<>();
        final List<Person> contacts = new ArrayList<>();

        @Override
        public boolean hasEvent(Event event) {
            requireNonNull(event);
            return eventsAdded.stream().anyMatch(event::isSameEvent);
        }

        @Override
        public void addEvent(Event event) {
            requireNonNull(event);
            eventsAdded.add(event);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return FXCollections.observableList(contacts);
        }
    }
}
