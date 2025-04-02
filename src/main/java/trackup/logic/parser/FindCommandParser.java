package trackup.logic.parser;

import static trackup.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import trackup.logic.commands.FindCommand;
import trackup.logic.parser.exceptions.ParseException;
import trackup.model.person.Name;
import trackup.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        // Validate each keyword using Name.isValidName()
        for (String keyword : nameKeywords) {
            if (!Name.isValidName(keyword)) {
                throw new ParseException(Name.MESSAGE_CONSTRAINTS);
            }
        }

        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }


}
