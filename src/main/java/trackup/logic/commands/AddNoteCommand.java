package trackup.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import trackup.commons.core.index.Index;
import trackup.logic.Messages;
import trackup.logic.commands.exceptions.CommandException;
import trackup.model.Model;
import trackup.model.note.Note;
import trackup.model.person.Person;

/**
 * Adds a note to a person in the address book.
 */
public class AddNoteCommand extends Command {

    public static final String COMMAND_WORD = "addnote";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a short note to the specified person in TrackUp.\n"
            + "Parameter(s): "
            + "<PERSON_INDEX> <NOTE_TEXT>\n"
            + "Example: " + COMMAND_WORD + " 1 Met at networking event, follow up next week";

    public static final String MESSAGE_SUCCESS = "New note added to %1$s: \"%2$s\"";

    private final Index personIndex;
    private final String noteContent;

    /**
     * Creates an AddNoteCommand to add a note to the person at the given index.
     */
    public AddNoteCommand(Index personIndex, String noteContent) {
        requireNonNull(personIndex);
        requireNonNull(noteContent);
        this.personIndex = personIndex;
        this.noteContent = noteContent;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(personIndex.getZeroBased());
        Note newNote = new Note(noteContent);

        boolean added = personToEdit.addNote(newNote);
        if (!added) {
            return new CommandResult(String.format(
                    "%s already has the maximum number of notes (%s). Note not added.",
                    personToEdit.getName(), Person.MAX_NOTES));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, personToEdit.getName(), noteContent));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddNoteCommand)) {
            return false;
        }

        AddNoteCommand otherCommand = (AddNoteCommand) other;
        return personIndex.equals(otherCommand.personIndex)
                && noteContent.equals(otherCommand.noteContent);
    }

    @Override
    public String toString() {
        return String.format("AddNoteCommand to index %s: %s", personIndex.getOneBased(), noteContent);
    }
}
