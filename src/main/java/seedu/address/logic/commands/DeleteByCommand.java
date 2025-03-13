package seedu.address.logic.commands;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

import java.util.function.Predicate;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

/**
 * Deletes a person identified using it's attributes from the address book.
 */
public class DeleteByCommand extends Command {

    public static final String COMMAND_WORD = "delete by";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by any attribute used to identify the person.\n"
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS"
            + PREFIX_TAG + "TAG\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_NO_PERSON_TO_DELETE = "No Person Matches Criteria: %1$s";
    public static final String MESSAGE_MULTIPLE_PEOPLE_TO_DELETE = "Multiple People Matches Criteria: %1$s";

    private final Optional<Name> deleteByName;
    private final Optional<Phone> deleteByPhone;
    private final Optional<Email> deleteByEmail;
    private final Optional<Address> deleteByAddress;
    private final Optional<Tag> deleteByTag;

    public DeleteByCommand(Optional<Name> name, Optional<Phone> phone, Optional<Email> email, Optional<Address> address,
                           Optional<Tag> tag) {
        this.deleteByName = name;
        this.deleteByPhone = phone;
        this.deleteByEmail = email;
        this.deleteByAddress = address;
        this.deleteByTag = tag;
    }

    /**
     * Creates a predicate to filter persons based on optional deletion criteria.
     * If a criterion is present, it checks if the person's attribute matches the given value.
     * If a criterion is absent, it does not filter by that attribute.
     *
     * @return A {@link Predicate} that can be used to filter a list of {@link Person} objects.
     */
    private Predicate<Person> getPredicate() {
        return person -> deleteByName.map(name -> name.equals(person.getName())).orElse(true) &&
                deleteByPhone.map(phone -> phone.equals(person.getPhone())).orElse(true) &&
                deleteByEmail.map(email -> email.equals(person.getEmail())).orElse(true) &&
                deleteByAddress.map(address -> address.equals(person.getAddress())).orElse(true) &&
                deleteByTag.map(tag -> person.getTags().contains(tag)).orElse(true);
    }

    /**
     * Adds optional deletion criteria to a {@link ToStringBuilder} instance.
     * If a criterion is present, it is included in the string representation.
     *
     * @param stringBuilder The {@link ToStringBuilder} instance to append criteria to.
     */
    private void AddCriteriaToStringBuilder(ToStringBuilder stringBuilder) {
        deleteByName.ifPresent(value -> stringBuilder.add("name", value));
        deleteByPhone.ifPresent(value -> stringBuilder.add("phone", value));
        deleteByEmail.ifPresent(value -> stringBuilder.add("email", value));
        deleteByAddress.ifPresent(value -> stringBuilder.add("address", value));
        deleteByTag.ifPresent(value -> stringBuilder.add("tag", value));
    }

    /**
     * Formats the deletion criteria into a human-readable string.
     * The output includes only the criteria that are present.
     *
     * @return A formatted string representation of the deletion criteria.
     */
    private String formatPersonDetails() {
        ToStringBuilder stringBuilder = new ToStringBuilder("Criteria");
        AddCriteriaToStringBuilder(stringBuilder);
        return stringBuilder.toString();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // delete by should always draw from full address book
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        Predicate<Person> predicate = getPredicate();

        List<Person> filteredList = model.getFilteredPersonList().stream().filter(getPredicate()).toList();

        if (filteredList.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_NO_PERSON_TO_DELETE, formatPersonDetails()));
        } else if (filteredList.size() == 1) {
            Person personToDelete = filteredList.get(0);
            model.deletePerson(personToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
        } else {
            model.updateFilteredPersonList(predicate);
            return new CommandResult(String.format(MESSAGE_MULTIPLE_PEOPLE_TO_DELETE, formatPersonDetails()));
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

        return deleteByName.equals(otherDeleteCommand.deleteByName) &&
                deleteByPhone.equals(otherDeleteCommand.deleteByPhone) &&
                deleteByEmail.equals(otherDeleteCommand.deleteByEmail) &&
                deleteByAddress.equals(otherDeleteCommand.deleteByAddress) &&
                deleteByTag.equals(otherDeleteCommand.deleteByTag);
    }

    @Override
    public String toString() {
        ToStringBuilder stringBuilder = new ToStringBuilder(this);
        AddCriteriaToStringBuilder(stringBuilder);
        return stringBuilder.toString();
    }
}
