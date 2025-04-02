package trackup.testutil;

import static trackup.logic.parser.CliSyntax.PREFIX_EVENT_CONTACT;
import static trackup.logic.parser.CliSyntax.PREFIX_EVENT_END;
import static trackup.logic.parser.CliSyntax.PREFIX_EVENT_START;
import static trackup.logic.parser.CliSyntax.PREFIX_EVENT_TITLE;

import java.time.format.DateTimeFormatter;
import java.util.Set;

import trackup.commons.core.index.Index;
import trackup.logic.commands.AddEventCommand;
import trackup.model.event.Event;

/**
 * A utility class for Event.
 */
public class EventUtil {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Returns an add event command string for adding the {@code event}.
     */
    public static String getAddEventCommand(Event event, Set<Index> contactIndexes) {
        return AddEventCommand.COMMAND_WORD + " " + getEventDetails(event, contactIndexes);
    }

    /**
     * Returns the part of command string for the given {@code event}'s details.
     */
    public static String getEventDetails(Event event, Set<Index> contactIndexes) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_EVENT_TITLE).append(event.getTitle()).append(" ");
        sb.append(PREFIX_EVENT_START).append(event.getStartDateTime().format(FORMATTER)).append(" ");
        sb.append(PREFIX_EVENT_END).append(event.getEndDateTime().format(FORMATTER)).append(" ");
        contactIndexes.forEach(index -> sb.append(PREFIX_EVENT_CONTACT).append(index.getOneBased()).append(" "));
        return sb.toString();
    }
}

