package trackup.logic.parser;

import static trackup.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static trackup.logic.parser.CliSyntax.PREFIX_EVENT_CONTACT;
import static trackup.logic.parser.CliSyntax.PREFIX_EVENT_END;
import static trackup.logic.parser.CliSyntax.PREFIX_EVENT_START;
import static trackup.logic.parser.CliSyntax.PREFIX_EVENT_TITLE;

import java.time.LocalDateTime;
import java.util.Set;

import trackup.commons.core.index.Index;
import trackup.logic.commands.DeleteEventCommand;
import trackup.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteEventCommand object.
 */
public class DeleteEventCommandParser implements Parser<DeleteEventCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteEventCommand
     * and returns a DeleteEventCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public DeleteEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_EVENT_TITLE, PREFIX_EVENT_START, PREFIX_EVENT_END, PREFIX_EVENT_CONTACT);

        String titleKeyword = argMultimap.getValue(PREFIX_EVENT_TITLE).orElse(null);
        LocalDateTime startTime = ParserUtil.parseEventTime(argMultimap.getValue(PREFIX_EVENT_START).orElse(null));
        LocalDateTime endTime = ParserUtil.parseEventTime(argMultimap.getValue(PREFIX_EVENT_END).orElse(null));
        Set<Index> contactIndexes = ParserUtil.parseContacts(argMultimap.getAllValues(PREFIX_EVENT_CONTACT));

        if (titleKeyword == null && startTime == null && endTime == null && contactIndexes.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE));
        }

        return new DeleteEventCommand(titleKeyword, startTime, endTime, contactIndexes);
    }
}
