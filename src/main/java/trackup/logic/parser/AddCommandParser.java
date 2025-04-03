package trackup.logic.parser;

import static trackup.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static trackup.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static trackup.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static trackup.logic.parser.CliSyntax.PREFIX_EMAIL;
import static trackup.logic.parser.CliSyntax.PREFIX_NAME;
import static trackup.logic.parser.CliSyntax.PREFIX_PHONE;
import static trackup.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import trackup.logic.commands.AddCommand;
import trackup.logic.parser.exceptions.ParseException;
import trackup.model.category.Category;
import trackup.model.person.Address;
import trackup.model.person.Email;
import trackup.model.person.Name;
import trackup.model.person.Person;
import trackup.model.person.Phone;
import trackup.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_TAG, PREFIX_CATEGORY);

        // Fail early if unexpected preamble
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        // Check all required fields and collect any that are missing
        List<String> missingFields = new ArrayList<>();
        if (argMultimap.getValue(PREFIX_NAME).isEmpty()) {
            missingFields.add("Name (-n)");
        }
        if (argMultimap.getValue(PREFIX_PHONE).isEmpty()) {
            missingFields.add("Phone (-p)");
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isEmpty()) {
            missingFields.add("Email (-e)");
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isEmpty()) {
            missingFields.add("Address (-a)");
        }

        // If any required field is missing, throw a descriptive error
        if (!missingFields.isEmpty()) {
            StringBuilder errorMessageBuilder = new StringBuilder("Missing required field");
            if (missingFields.size() > 1) {
                errorMessageBuilder.append("s");
            }
            errorMessageBuilder.append(": ").append(String.join(", ", missingFields));
            throw new ParseException(errorMessageBuilder.toString());
        }

        // No duplicates allowed for single-value fields
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Optional<Category> category = argMultimap.getValue(PREFIX_CATEGORY).isPresent()
                ? Optional.ofNullable(ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get()))
                : Optional.empty();

        Person person = new Person(name, phone, email, address, tagList, category);
        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
