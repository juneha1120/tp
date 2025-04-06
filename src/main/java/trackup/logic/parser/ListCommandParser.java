package trackup.logic.parser;

import java.util.Optional;

import trackup.logic.commands.ListCommand;
import trackup.logic.parser.exceptions.ParseException;
import trackup.model.category.Category;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        Optional<Category> category = Optional.empty();

        if (!trimmedArgs.isEmpty()) {
            trimmedArgs = trimmedArgs.substring(0, 1).toUpperCase() + trimmedArgs.substring(1).toLowerCase();
            if (!Category.isValidCategoryName(trimmedArgs)) {
                throw new ParseException("Invalid category: '" + trimmedArgs
                        + "'. Use one of the supported categories: Client, Partner, Investor, Other.");
            }

            category = Optional.of(new Category(trimmedArgs));
        }

        return new ListCommand(category);
    }
}

