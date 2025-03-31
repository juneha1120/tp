package trackup.logic.parser;

import static trackup.logic.Messages.MESSAGE_END_BEFORE_START;
import static trackup.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static trackup.logic.parser.CliSyntax.PREFIX_EVENT_CONTACT;
import static trackup.logic.parser.CliSyntax.PREFIX_EVENT_END;
import static trackup.logic.parser.CliSyntax.PREFIX_EVENT_START;
import static trackup.logic.parser.CliSyntax.PREFIX_EVENT_TITLE;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Stream;

import trackup.commons.core.index.Index;
import trackup.logic.commands.AddEventCommand;
import trackup.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddEventCommand object.
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddEventCommand
     * and returns an AddEventCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public AddEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EVENT_TITLE, PREFIX_EVENT_START, PREFIX_EVENT_END,
                        PREFIX_EVENT_CONTACT);

        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_TITLE, PREFIX_EVENT_START, PREFIX_EVENT_END)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_EVENT_TITLE, PREFIX_EVENT_START, PREFIX_EVENT_END);
        String title = ParserUtil.parseEventTitle(argMultimap.getValue(PREFIX_EVENT_TITLE).get());
        LocalDateTime startTime = ParserUtil.parseEventTime(argMultimap.getValue(PREFIX_EVENT_START).get());
        LocalDateTime endTime = ParserUtil.parseEventTime(argMultimap.getValue(PREFIX_EVENT_END).get());

        if (!endTime.isAfter(startTime)) {
            throw new ParseException(MESSAGE_END_BEFORE_START);
        }

        Set<Index> contacts = ParserUtil.parseContacts(argMultimap.getAllValues(PREFIX_EVENT_CONTACT));

        return new AddEventCommand(title, startTime, endTime, contacts);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
