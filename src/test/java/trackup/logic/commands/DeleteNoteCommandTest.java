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
import trackup.model.person.Person;
import trackup.testutil.PersonBuilder;

public class DeleteNoteCommandTest {

    @Mock
    private Model model;

    private Person personWithNotes;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        personWithNotes = new PersonBuilder()
                .withNotes("Note 1", "Note 2", "Note 3")
                .build();
    }

    @Test
    public void execute_validIndices_deletesNoteSuccessfully() throws Exception {
        when(model.getFilteredPersonList()).thenReturn(FXCollections.observableArrayList(personWithNotes));

        Index personIndex = Index.fromZeroBased(0);
        Index noteIndex = Index.fromZeroBased(1); // deleting "Note 2"

        DeleteNoteCommand command = new DeleteNoteCommand(personIndex, noteIndex);
        CommandResult result = command.execute(model);

        assertEquals(String.format(DeleteNoteCommand.MESSAGE_SUCCESS, 2, personWithNotes.getName()),
                result.getFeedbackToUser());
        assertEquals(2, personWithNotes.getNotes().size());
        assertEquals("Note 1", personWithNotes.getNotes().get(0).text);
        assertEquals("Note 3", personWithNotes.getNotes().get(1).text);
    }

    @Test
    public void execute_noNotes_throwsCommandException() {
        Person noNotesPerson = new PersonBuilder().build();
        when(model.getFilteredPersonList()).thenReturn(FXCollections.observableArrayList(noNotesPerson));

        DeleteNoteCommand command = new DeleteNoteCommand(Index.fromOneBased(1), Index.fromOneBased(1));
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_invalidNoteIndex_throwsCommandException() {
        when(model.getFilteredPersonList()).thenReturn(FXCollections.observableArrayList(personWithNotes));

        Index invalidNoteIndex = Index.fromOneBased(5); // only 3 notes exist

        DeleteNoteCommand command = new DeleteNoteCommand(Index.fromOneBased(1), invalidNoteIndex);
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        when(model.getFilteredPersonList()).thenReturn(FXCollections.observableArrayList()); // empty list

        DeleteNoteCommand command = new DeleteNoteCommand(Index.fromOneBased(1), Index.fromOneBased(1));
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        DeleteNoteCommand c1 = new DeleteNoteCommand(Index.fromOneBased(1), Index.fromOneBased(2));
        DeleteNoteCommand c2 = new DeleteNoteCommand(Index.fromOneBased(1), Index.fromOneBased(2));
        assertEquals(c1, c2);
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        DeleteNoteCommand c1 = new DeleteNoteCommand(Index.fromOneBased(1), Index.fromOneBased(2));
        DeleteNoteCommand c2 = new DeleteNoteCommand(Index.fromOneBased(1), Index.fromOneBased(3));
        DeleteNoteCommand c3 = new DeleteNoteCommand(Index.fromOneBased(2), Index.fromOneBased(2));
        assertNotEquals(c1, c2);
        assertNotEquals(c1, c3);
    }

    @Test
    public void toString_returnsExpectedFormat() {
        DeleteNoteCommand command = new DeleteNoteCommand(Index.fromOneBased(1), Index.fromOneBased(2));
        String expected = "DeleteNoteCommand: PersonIndex=1, NoteIndex=2";
        assertEquals(expected, command.toString());
    }
}
