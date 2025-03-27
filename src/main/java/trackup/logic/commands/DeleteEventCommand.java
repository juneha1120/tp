package trackup.logic.commands;

import static java.util.Objects.requireNonNull;
import static trackup.logic.parser.CliSyntax.PREFIX_EVENT_CONTACT;
import static trackup.logic.parser.CliSyntax.PREFIX_EVENT_END;
import static trackup.logic.parser.CliSyntax.PREFIX_EVENT_START;
import static trackup.logic.parser.CliSyntax.PREFIX_EVENT_TITLE;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import trackup.commons.core.index.Index;
import trackup.commons.util.ToStringBuilder;
import trackup.logic.Messages;
import trackup.logic.commands.exceptions.CommandException;
import trackup.model.Model;
import trackup.model.event.Event;
import trackup.model.person.Person;

/**
 * Deletes event from the calendar based on filters.
 *
 */
public class DeleteEventCommand extends Command {

    public static final String COMMAND_WORD = "delevent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes events that match the given filters.\n"
            + "Parameters: "
            + "[" + PREFIX_EVENT_TITLE + "TITLE_KEYWORD] "
            + "[" + PREFIX_EVENT_START + "START_DATETIME] "
            + "[" + PREFIX_EVENT_END + "END_DATETIME] "
            + "[" + PREFIX_EVENT_CONTACT + "CONTACT_INDEX]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EVENT_TITLE + "Meeting ";

    public static final String MESSAGE_SUCCESS = "Deleted event(s): %1$s";
    public static final String MESSAGE_EVENT_NOT_FOUND = "No matching events found.";

    private final String partialTitle;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final Set<Index> contactIndexes;

    /**
     * Constructs a {@code DeleteEventCommand} with the given filtering parameters.
     *
     * @param partialTitle
     *      A partial title to match events (case-insensitive). Can be {@code null} if not filtering by title.
     * @param startDateTime
     *      The exact start date-time of the event to match. Can be {@code null} if not filtering by start time.
     * @param endDateTime
     *      The exact end date-time of the event to match. Can be {@code null} if not filtering by end time.
     * @param contactIndexes
     *      A set of contact indexes representing contacts linked to the events to delete.
     *                        Can be empty if not filtering by contacts.
     */
    public DeleteEventCommand(String partialTitle, LocalDateTime startDateTime, LocalDateTime endDateTime,
                              Set<Index> contactIndexes) {
        this.partialTitle = partialTitle;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.contactIndexes = contactIndexes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> eventList = model.getEventList();
        List<Person> contactList = model.getFilteredPersonList();

        Set<Person> contacts = contactIndexes.stream()
                .filter(index -> index.getZeroBased() < contactList.size())
                .map(index -> contactList.get(index.getZeroBased()))
                .collect(Collectors.toSet());

        // Find matching events based on given criteria
        List<Event> eventsToDelete = eventList.stream()
                .filter(event -> (partialTitle == null || event.getTitle().toLowerCase()
                        .contains(partialTitle.toLowerCase()))
                        && (startDateTime == null || event.getStartDateTime().equals(startDateTime))
                        && (endDateTime == null || event.getEndDateTime().equals(endDateTime))
                        && (contacts.isEmpty() || event.getContacts().stream().anyMatch(contacts::contains)))
                .toList();

        if (eventsToDelete.isEmpty()) {
            throw new CommandException(MESSAGE_EVENT_NOT_FOUND);
        }

        eventsToDelete.forEach(model::deleteEvent);
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                eventsToDelete.stream().map(Messages::format).collect(Collectors.joining("\n"))));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteEventCommand)) {
            return false;
        }

        DeleteEventCommand otherCommand = (DeleteEventCommand) other;
        return partialTitle.equals(otherCommand.partialTitle)
                && startDateTime.equals(otherCommand.startDateTime)
                && endDateTime.equals(otherCommand.endDateTime)
                && contactIndexes.equals(otherCommand.contactIndexes);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("partialTitle", partialTitle)
                .add("startDateTime", startDateTime)
                .add("endDateTime", endDateTime)
                .add("contactIndexes", contactIndexes)
                .toString();
    }

}
