package trackup.model.person;

import static trackup.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import trackup.commons.core.index.Index;
import trackup.commons.util.ToStringBuilder;
import trackup.model.category.Category;
import trackup.model.note.Note;
import trackup.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    public static final int MAX_NOTES = 5;

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Optional<Category> category;
    private final ObservableList<Note> notes;
    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Optional<Category> category) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.category = category; // can be Optional.empty()
        this.notes = FXCollections.observableArrayList();
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Optional<Category> getCategory() {
        return category;
    }

    public boolean hasCategory(Category category) {
        return this.category.isPresent() && this.category.get().equals(category);
    }

    public ObservableList<Note> getNotes() {
        return notes;
    }

    /**
     * Adds a note to the person's list of notes if the maximum limit has not been reached.
     *
     * @param note the note to add.
     * @return true if the note was successfully added, false if the person already has the maximum number of notes.
     */
    public boolean addNote(Note note) {
        if (notes.size() >= MAX_NOTES) {
            return false;
        }
        notes.add(note);
        return true;
    }

    /**
     * Removes the note at the specified index from the person's list of notes.
     *
     * @param index the zero-based index of the note to remove.
     * @throws IndexOutOfBoundsException if the index is invalid.
     */
    public void removeNote(Index index) {
        notes.remove(index.getZeroBased());
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }
        return otherPerson != null
                && otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person otherPerson)) {
            return false;
        }

        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags)
                && category.equals(otherPerson.category)
                && notes.equals(otherPerson.notes);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, category, notes);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .add("category", category.orElse(null))
                .add("notes", notes)
                .toString();
    }

}
