package trackup.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import trackup.logic.parser.Prefix;
import trackup.model.event.Event;
import trackup.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d person(s) listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_END_BEFORE_START = "End datetime provided is before start datetime";

    public static final String MESSAGE_MULTIPLE_PEOPLE_TO_DELETE =
            "Multiple contacts match the provided attributes: %s. "
                    + "Please refine your input to uniquely identify a contact.";

    public static final String MESSAGE_NO_PERSON_TO_DELETE =
            "No Person Matches Criteria: %1$s";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Address: ")
                .append(person.getAddress())
                .append("; Tags: ");
        person.getTags().forEach(builder::append);
        if (person.getCategory().isPresent()) {
            builder.append("; Category: ").append(person.getCategory().get().categoryName);
        }
        return builder.toString();
    }

    /**
     * Formats the {@code event} for display to the user.
     */
    public static String format(Event event) {
        final StringBuilder builder = new StringBuilder();
        builder.append(event.getTitle())
                .append("; Start: ")
                .append(event.getStartDateTime())
                .append("; End: ")
                .append(event.getEndDateTime())
                .append("; Contacts: ");
        event.getContacts().forEach(builder::append);
        return builder.toString();
    }

}
