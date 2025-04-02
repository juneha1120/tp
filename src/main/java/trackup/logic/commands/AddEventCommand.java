package trackup.logic.commands;

import static java.util.Objects.requireNonNull;
import static trackup.logic.parser.CliSyntax.PREFIX_EVENT_CONTACT;
import static trackup.logic.parser.CliSyntax.PREFIX_EVENT_END;
import static trackup.logic.parser.CliSyntax.PREFIX_EVENT_START;
import static trackup.logic.parser.CliSyntax.PREFIX_EVENT_TITLE;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import trackup.commons.core.index.Index;
import trackup.commons.util.ToStringBuilder;
import trackup.logic.Messages;
import trackup.logic.commands.exceptions.CommandException;
import trackup.model.Model;
import trackup.model.event.Event;
import trackup.model.person.Person;

/**
 * Adds an event to the calendar.
 */
public class AddEventCommand extends Command {

    public static final String COMMAND_WORD = "addevent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the calendar. "
            + "Parameters: "
            + PREFIX_EVENT_TITLE + "EVENT_TITLE "
            + PREFIX_EVENT_START + "START_DATETIME "
            + PREFIX_EVENT_END + "END_DATETIME "
            + "[" + PREFIX_EVENT_CONTACT + "CONTACT_INDEX1 " + PREFIX_EVENT_CONTACT + "CONTACT_INDEX2,...]"
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_EVENT_TITLE + "Team Meeting "
            + PREFIX_EVENT_START + "2025-03-30 14:00 "
            + PREFIX_EVENT_END + "2025-03-30 15:00 "
            + PREFIX_EVENT_CONTACT + "1 "
            + PREFIX_EVENT_CONTACT + "3";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the calendar";

    private final String eventName;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final Set<Index> contactIndexes;

    /**
     * Creates an AddEventCommand to add the specified {@code Event}.
     */
    public AddEventCommand(String eventName,
                           LocalDateTime startDateTime, LocalDateTime endDateTime,
                           Set<Index> contactIndexes) {
        requireNonNull(eventName);
        requireNonNull(startDateTime);
        requireNonNull(endDateTime);
        this.eventName = eventName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.contactIndexes = (contactIndexes != null) ? contactIndexes : new HashSet<>();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        Set<Person> linkedContacts = new HashSet<>();
        for (Index index : contactIndexes) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            linkedContacts.add(lastShownList.get(index.getZeroBased()));
        }

        Event newEvent = new Event(eventName, startDateTime, endDateTime, linkedContacts);
        if (model.hasEvent(newEvent)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        model.addEvent(newEvent);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(newEvent)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddEventCommand)) {
            return false;
        }

        AddEventCommand otherCommand = (AddEventCommand) other;
        return eventName.equals(otherCommand.eventName)
                && startDateTime.equals(otherCommand.startDateTime)
                && endDateTime.equals(otherCommand.endDateTime)
                && contactIndexes.equals(otherCommand.contactIndexes);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("eventName", eventName)
                .add("startDateTime", startDateTime)
                .add("endDateTime", endDateTime)
                .add("contactIndexes", contactIndexes)
                .toString();
    }
}
