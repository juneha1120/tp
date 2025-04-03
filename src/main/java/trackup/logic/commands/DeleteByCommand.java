package trackup.logic.commands;

import static java.util.Objects.requireNonNull;
import static trackup.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static trackup.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static trackup.logic.parser.CliSyntax.PREFIX_EMAIL;
import static trackup.logic.parser.CliSyntax.PREFIX_NAME;
import static trackup.logic.parser.CliSyntax.PREFIX_PHONE;
import static trackup.logic.parser.CliSyntax.PREFIX_TAG;
import static trackup.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import trackup.commons.util.ToStringBuilder;
import trackup.logic.Messages;
import trackup.logic.commands.exceptions.CommandException;
import trackup.model.Model;
import trackup.model.category.Category;
import trackup.model.event.Event;
import trackup.model.person.Address;
import trackup.model.person.Email;
import trackup.model.person.Name;
import trackup.model.person.Person;
import trackup.model.person.Phone;
import trackup.model.tag.Tag;


/**
 * Deletes a person identified using its attributes from the address book.
 */
public class DeleteByCommand extends Command {

    public static final String COMMAND_WORD = "deleteby";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by any attribute used to identify the person.\n"
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS"
            + PREFIX_TAG + "TAG"
            + PREFIX_CATEGORY + "CATEGORY\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends"
            + PREFIX_CATEGORY + "client";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_MULTIPLE_PEOPLE_TO_DELETE =
        "Multiple contacts match the provided attributes: %1$s. "
            + "Please refine your input to uniquely identify a contact.";

    public static final String MESSAGE_NO_PERSON_TO_DELETE =
        "No Person Matches Criteria: %1$s";

    public static final String MESSAGE_NO_CRITERIA_SPECIFIED =
        "At least one attribute must be provided to delete a contact.";


    private final Optional<Name> deleteByName;
    private final Optional<Phone> deleteByPhone;
    private final Optional<Email> deleteByEmail;
    private final Optional<Address> deleteByAddress;
    private final Optional<Tag> deleteByTag;
    private final Optional<Category> deleteByCategory;

    /**
     * Constructs a {@code DeleteByCommand} with optional filtering criteria.
     * Each parameter represents an optional field that may be used to identify
     * a person for deletion. If a parameter is present, it will be used as a
     * criterion for filtering. If all parameters are empty, the command is invalid.
     *
     * @param name     An optional {@link Name} used as a deletion criterion.
     * @param phone    An optional {@link Phone} used as a deletion criterion.
     * @param email    An optional {@link Email} used as a deletion criterion.
     * @param address  An optional {@link Address} used as a deletion criterion.
     * @param tag      An optional {@link Tag} used as a deletion criterion.
     * @param category An optional {@link Category} used as a deletion criterion.
     */
    public DeleteByCommand(Optional<Name> name, Optional<Phone> phone, Optional<Email> email, Optional<Address> address,
                           Optional<Tag> tag, Optional<Category> category) {
        requireNonNull(name);
        requireNonNull(phone);
        requireNonNull(email);
        requireNonNull(address);
        requireNonNull(tag);
        requireNonNull(category);
        this.deleteByName = name;
        this.deleteByPhone = phone;
        this.deleteByEmail = email;
        this.deleteByAddress = address;
        this.deleteByTag = tag;
        this.deleteByCategory = category;
    }

    /**
     * Creates a predicate to filter persons based on optional deletion criteria.
     * If a criterion is present, it checks if the person's attribute matches the given value.
     * If a criterion is absent, it does not filter by that attribute.
     *
     * @return A {@link Predicate} that can be used to filter a list of {@link Person} objects.
     */
    Predicate<Person> getPredicate() {
        return person -> deleteByName.map(name -> name.equals(person.getName())).orElse(true)
                && deleteByPhone.map(phone -> phone.equals(person.getPhone())).orElse(true)
                && deleteByEmail.map(email -> email.equals(person.getEmail())).orElse(true)
                && deleteByAddress.map(address -> address.equals(person.getAddress())).orElse(true)
                && deleteByTag.map(tag -> person.getTags().contains(tag)).orElse(true)
                && deleteByCategory.map(category -> category.equals(person.getCategory())).orElse(true);
    }

    /**
     * Adds optional deletion criteria to a {@link ToStringBuilder} instance.
     * If a criterion is present, it is included in the string representation.
     *
     * @param stringBuilder The {@link ToStringBuilder} instance to append criteria to.
     */
    void addCriteriaToStringBuilder(ToStringBuilder stringBuilder) {
        deleteByName.ifPresent(value -> stringBuilder.add("name", value));
        deleteByPhone.ifPresent(value -> stringBuilder.add("phone", value));
        deleteByEmail.ifPresent(value -> stringBuilder.add("email", value));
        deleteByAddress.ifPresent(value -> stringBuilder.add("address", value));
        deleteByTag.ifPresent(value -> stringBuilder.add("tag", value));
        deleteByCategory.ifPresent(value -> stringBuilder.add("category", value));
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // delete by should always draw from full address book
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        Predicate<Person> predicate = getPredicate();

        List<Person> filteredList = model.getFilteredPersonList().stream().filter(getPredicate()).toList();

        if (filteredList.isEmpty()) {
            throw new CommandException(MESSAGE_NO_PERSON_TO_DELETE);
        } else if (filteredList.size() == 1) {
            Person personToDelete = filteredList.get(0);

            // Update all events linked to this contact
            List<Event> allEvents = model.getEventList();
            for (Event event : allEvents) {
                if (event.getContacts().contains(personToDelete)) {
                    Set<Person> updatedContacts = new HashSet<>(event.getContacts());
                    updatedContacts.remove(personToDelete);

                    Event updatedEvent = new Event(event.getTitle(), event.getStartDateTime(),
                            event.getEndDateTime(), updatedContacts);
                    model.setEvent(event, updatedEvent);
                }
            }

            model.deletePerson(personToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
        } else {
            model.updateFilteredPersonList(predicate);
            return new CommandResult(MESSAGE_MULTIPLE_PEOPLE_TO_DELETE);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteByCommand otherDeleteCommand)) {
            return false;
        }

        return deleteByName.equals(otherDeleteCommand.deleteByName)
                && deleteByPhone.equals(otherDeleteCommand.deleteByPhone)
                && deleteByEmail.equals(otherDeleteCommand.deleteByEmail)
                && deleteByAddress.equals(otherDeleteCommand.deleteByAddress)
                && deleteByTag.equals(otherDeleteCommand.deleteByTag)
                && deleteByCategory.equals(otherDeleteCommand.deleteByCategory);
    }

    @Override
    public String toString() {
        ToStringBuilder stringBuilder = new ToStringBuilder(this);
        addCriteriaToStringBuilder(stringBuilder);
        return stringBuilder.toString();
    }
}
