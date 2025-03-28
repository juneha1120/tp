package trackup.logic.parser;

import static trackup.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import trackup.logic.commands.ToggleCommand;
import trackup.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new HideCommand object
 */
public class ToggleCommandParser implements Parser<ToggleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the HideCommand
     * and returns a HideCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public ToggleCommand parse(String args) throws ParseException {
        String fieldName = args.trim().toLowerCase();

        return switch (fieldName) {
        case ToggleCommand.NAME_FIELD_STRING, ToggleCommand.PHONE_FIELD_STRING, ToggleCommand.EMAIL_FIELD_STRING,
                ToggleCommand.ADDRESS_FIELD_STRING, ToggleCommand.TAG_FIELD_STRING, ToggleCommand.CATEGORY_FIELD_STRING
                -> new ToggleCommand(fieldName);
        default ->
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ToggleCommand.MESSAGE_USAGE));
        };
    }
}
