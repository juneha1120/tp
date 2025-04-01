package trackup.logic.parser;

import static trackup.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import trackup.commons.core.index.Index;
import trackup.logic.commands.DeleteNoteCommand;
import trackup.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code DeleteNoteCommand} object.
 * Expected format: delnote PERSON_INDEX NOTE_INDEX
 */
public class DeleteNoteCommandParser implements Parser<DeleteNoteCommand> {

    private static final Pattern DELETE_NOTE_COMMAND_FORMAT = Pattern.compile("(?<personIndex>\\d+)\\s+(?<noteIndex>\\d+)");

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteNoteCommand
     * and returns a DeleteNoteCommand object for execution.
     *
     * @param args Full input string following the command word.
     * @return A {@code DeleteNoteCommand} for execution.
     * @throws ParseException if the input does not conform to the expected format.
     */
    @Override
    public DeleteNoteCommand parse(String args) throws ParseException {
        final Matcher matcher = DELETE_NOTE_COMMAND_FORMAT.matcher(args.trim());

        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteNoteCommand.MESSAGE_USAGE));
        }

        Index personIndex = ParserUtil.parseIndex(matcher.group("personIndex"));
        Index noteIndex = ParserUtil.parseIndex(matcher.group("noteIndex"));

        return new DeleteNoteCommand(personIndex, noteIndex);
    }
}
