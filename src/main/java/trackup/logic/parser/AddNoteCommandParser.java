package trackup.logic.parser;

import static trackup.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import trackup.commons.core.index.Index;
import trackup.logic.commands.AddNoteCommand;
import trackup.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code AddNoteCommand} object.
 * Expected format: addnote PERSON_INDEX NOTE_TEXT
 */
public class AddNoteCommandParser implements Parser<AddNoteCommand> {

    private static final Pattern ADD_NOTE_COMMAND_FORMAT = Pattern.compile("(?<index>\\d+)\\s+(?<note>.+)");

    /**
     * Parses the given {@code String} of arguments in the context of the AddNoteCommand
     * and returns an AddNoteCommand object for execution.
     *
     * @param args Full input string following the command word.
     * @return An {@code AddNoteCommand} for execution.
     * @throws ParseException if the input does not conform to the expected format.
     */
    @Override
    public AddNoteCommand parse(String args) throws ParseException {
        final Matcher matcher = ADD_NOTE_COMMAND_FORMAT.matcher(args.trim());

        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddNoteCommand.MESSAGE_USAGE));
        }

        Index personIndex = ParserUtil.parseIndex(matcher.group("index"));
        String noteContent = matcher.group("note").trim();

        if (noteContent.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddNoteCommand.MESSAGE_USAGE));
        }

        return new AddNoteCommand(personIndex, noteContent);
    }
}
