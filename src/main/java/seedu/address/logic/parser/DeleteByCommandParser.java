package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Optional;

import seedu.address.logic.commands.DeleteByCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

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
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        // No duplicates for tag allowed in delete by command
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        Optional<Name> deleteByName = Optional.empty();
        Optional<Phone> deleteByPhone = Optional.empty();
        Optional<Email> deleteByEmail = Optional.empty();
        Optional<Address> deleteByAddress = Optional.empty();
        Optional<Tag> deleteByTag = Optional.empty();

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

        if (deleteByName.isEmpty() && deleteByPhone.isEmpty() && deleteByEmail.isEmpty()
                && deleteByAddress.isEmpty() && deleteByTag.isEmpty()) {
            throw new ParseException(DeleteByCommand.MESSAGE_NO_CRITERIA_SPECIFIED);
        }

        return new DeleteByCommand(deleteByName, deleteByPhone, deleteByEmail, deleteByAddress, deleteByTag);
    }


}
