package trackup.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import trackup.commons.core.index.Index;
import trackup.logic.Messages;
import trackup.logic.commands.exceptions.CommandException;
import trackup.model.Model;
import trackup.model.person.Person;

/**
 * Deletes a note from a person in the address book.
 */
public class DeleteNoteCommand extends Command {

    public static final String COMMAND_WORD = "delnote";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a note from the specified person.\n"
            + "Parameters: PERSON_INDEX NOTE_INDEX\n"
            + "Example: " + COMMAND_WORD + " 1 2";

    public static final String MESSAGE_SUCCESS = "Deleted note %1$d from %2$s.";
    public static final String MESSAGE_NO_NOTES = "%s has no notes.";
    public static final String MESSAGE_INVALID_NOTE_INDEX = "Invalid note index for %s.";

    private final Index personIndex;
    private final Index noteIndex;

    /**
     * Creates a DeleteNoteCommand to remove the note at {@code noteIndex} from the person at {@code personIndex}.
     */
    public DeleteNoteCommand(Index personIndex, Index noteIndex) {
        requireNonNull(personIndex);
        requireNonNull(noteIndex);
        this.personIndex = personIndex;
        this.noteIndex = noteIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person person = lastShownList.get(personIndex.getZeroBased());

        if (person.getNotes().isEmpty()) {
            throw new CommandException(String.format(MESSAGE_NO_NOTES, person.getName()));
        }

        if (noteIndex.getZeroBased() >= person.getNotes().size()) {
            throw new CommandException(String.format(MESSAGE_INVALID_NOTE_INDEX, person.getName()));
        }

        person.removeNote(noteIndex);
        return new CommandResult(String.format(MESSAGE_SUCCESS, noteIndex.getOneBased(), person.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteNoteCommand)) {
            return false;
        }

        DeleteNoteCommand otherCommand = (DeleteNoteCommand) other;
        return personIndex.equals(otherCommand.personIndex)
                && noteIndex.equals(otherCommand.noteIndex);
    }

    @Override
    public String toString() {
        return String.format("DeleteNoteCommand: PersonIndex=%d, NoteIndex=%d",
                personIndex.getOneBased(), noteIndex.getOneBased());
    }
}
