package trackup.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import trackup.commons.exceptions.IllegalValueException;
import trackup.model.note.Note;

/**
 * Jackson-friendly version of {@link Note}.
 */
class JsonAdaptedNote {

    private final String noteText;

    /**
     * Constructs a {@code JsonAdaptedNote} with the given {@code noteText}.
     */
    @JsonCreator
    public JsonAdaptedNote(String noteText) {
        this.noteText = noteText;
    }

    /**
     * Converts a given {@code Note} into this class for Jackson use.
     */
    public JsonAdaptedNote(Note source) {
        this.noteText = source.text;
    }

    @JsonValue
    public String getNoteText() {
        return noteText;
    }

    /**
     * Converts this Jackson-friendly adapted note object into the model's {@code Note} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the note.
     */
    public Note toModelType() throws IllegalValueException {
        if (!Note.isValidNote(noteText)) {
            throw new IllegalValueException(Note.MESSAGE_CONSTRAINTS);
        }
        return new Note(noteText);
    }
}
