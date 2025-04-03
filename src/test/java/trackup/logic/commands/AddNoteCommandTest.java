package trackup.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;
import static trackup.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javafx.collections.FXCollections;
import trackup.commons.core.index.Index;
import trackup.logic.commands.exceptions.CommandException;
import trackup.model.Model;
import trackup.model.note.Note;
import trackup.model.person.Person;
import trackup.testutil.PersonBuilder;

public class AddNoteCommandTest {

    @Mock
    private Model model;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void execute_validIndex_addsNoteSuccessfully() throws Exception {
        Person personWithNoNotes = new PersonBuilder().build();
        when(model.getFilteredPersonList()).thenReturn(FXCollections.observableArrayList(personWithNoNotes));

        String noteText = "Follow up on project";
        AddNoteCommand command = new AddNoteCommand(Index.fromZeroBased(0), noteText);

        CommandResult result = command.execute(model);

        assertEquals(String.format(AddNoteCommand.MESSAGE_SUCCESS, personWithNoNotes.getName(), noteText),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        when(model.getFilteredPersonList()).thenReturn(FXCollections.observableArrayList()); // empty list

        AddNoteCommand command = new AddNoteCommand(Index.fromOneBased(1), "Hello");

        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_maxNotesReached_doesNotAddNote() throws Exception {
        Person person = new PersonBuilder().build();
        person.addNote(new Note("Note 1"));
        person.addNote(new Note("Note 2"));
        person.addNote(new Note("Note 3"));
        person.addNote(new Note("Note 4"));
        person.addNote(new Note("Note 5"));

        when(model.getFilteredPersonList()).thenReturn(FXCollections.observableArrayList(person));

        AddNoteCommand command = new AddNoteCommand(Index.fromZeroBased(0), "Extra note");

        CommandResult result = command.execute(model);

        assertEquals(String.format("%s already has the maximum number of notes (5). Note not added.",
                person.getName()), result.getFeedbackToUser());
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        AddNoteCommand cmd1 = new AddNoteCommand(Index.fromOneBased(1), "Same note");
        AddNoteCommand cmd2 = new AddNoteCommand(Index.fromOneBased(1), "Same note");
        assertEquals(cmd1, cmd2);
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        AddNoteCommand cmd1 = new AddNoteCommand(Index.fromOneBased(1), "Note A");
        AddNoteCommand cmd2 = new AddNoteCommand(Index.fromOneBased(2), "Note A");
        AddNoteCommand cmd3 = new AddNoteCommand(Index.fromOneBased(1), "Note B");

        assertNotEquals(cmd1, cmd2);
        assertNotEquals(cmd1, cmd3);
    }

    @Test
    public void toString_correctFormat_returnsExpectedString() {
        Index index = Index.fromOneBased(2);
        String noteText = "Sample note text";
        AddNoteCommand command = new AddNoteCommand(index, noteText);

        String expected = "AddNoteCommand to index 2: Sample note text";
        assertEquals(expected, command.toString());
    }

}
