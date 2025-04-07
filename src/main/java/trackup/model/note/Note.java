package trackup.model.note;

import static java.util.Objects.requireNonNull;
import static trackup.commons.util.AppUtil.checkArgument;

/**
 * Represents a Note associated with a contact in the address book.
 * Guarantees: immutable; text is valid as declared in {@link #isValidNote(String)}
 */
public class Note {

    public static final int MAX_NOTE_LENGTH = 50;

    public static final String MESSAGE_CONSTRAINTS =
            String.format("Note text should be not more than %d characters", MAX_NOTE_LENGTH);

    public final String text;

    /**
     * Constructs a {@code Note}.
     *
     * @param text Content of the note.
     */
    public Note(String text) {
        requireNonNull(text);
        checkArgument(isValidNote(text), MESSAGE_CONSTRAINTS);
        this.text = text;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidNote(String text) {
        return text.length() <= MAX_NOTE_LENGTH;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Note)) {
            return false;
        }

        Note otherNote = (Note) other;
        return text.equals(otherNote.text);
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + text + ']';
    }
}
