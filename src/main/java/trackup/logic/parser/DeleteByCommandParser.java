package trackup.logic.parser;

import static java.util.Objects.requireNonNull;
import static trackup.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static trackup.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static trackup.logic.parser.CliSyntax.PREFIX_EMAIL;
import static trackup.logic.parser.CliSyntax.PREFIX_NAME;
import static trackup.logic.parser.CliSyntax.PREFIX_PHONE;
import static trackup.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Optional;

import trackup.logic.commands.DeleteByCommand;
import trackup.logic.parser.exceptions.ParseException;
import trackup.model.category.Category;
import trackup.model.person.Address;
import trackup.model.person.Email;
import trackup.model.person.Name;
import trackup.model.person.Phone;
import trackup.model.tag.Tag;

/**
 * Parses input arguments and creates a new DeleteByCommand object
 */
public class DeleteByCommandParser implements Parser<DeleteByCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteByCommand
     * and returns an DeleteByCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteByCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG, PREFIX_CATEGORY);

        // No duplicates for tag allowed in delete by command
        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG, PREFIX_CATEGORY);

        Optional<Name> deleteByName = Optional.empty();
        Optional<Phone> deleteByPhone = Optional.empty();
        Optional<Email> deleteByEmail = Optional.empty();
        Optional<Address> deleteByAddress = Optional.empty();
        Optional<Tag> deleteByTag = Optional.empty();
        Optional<Category> deleteByCategory = Optional.empty();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            deleteByName = Optional.of(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            deleteByPhone = Optional.of(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            deleteByEmail = Optional.of(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            deleteByAddress = Optional.of(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            deleteByTag = Optional.of(ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG).get()));
        }
        if (argMultimap.getValue(PREFIX_CATEGORY).isPresent()) {
            deleteByCategory = Optional.ofNullable(ParserUtil.parseCategory(
                    argMultimap.getValue(PREFIX_CATEGORY).get()));
        }

        if (deleteByName.isEmpty() && deleteByPhone.isEmpty() && deleteByEmail.isEmpty()
                && deleteByAddress.isEmpty() && deleteByTag.isEmpty() && deleteByCategory.isEmpty()) {
            throw new ParseException(DeleteByCommand.MESSAGE_NO_CRITERIA_SPECIFIED);
        }

        return new DeleteByCommand(
                deleteByName, deleteByPhone, deleteByEmail, deleteByAddress, deleteByTag, deleteByCategory);
    }


}
