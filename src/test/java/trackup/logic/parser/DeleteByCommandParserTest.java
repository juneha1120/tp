package trackup.logic.parser;

import static trackup.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static trackup.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static trackup.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static trackup.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static trackup.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static trackup.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static trackup.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static trackup.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static trackup.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static trackup.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static trackup.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static trackup.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static trackup.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static trackup.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static trackup.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static trackup.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static trackup.logic.parser.CliSyntax.PREFIX_EMAIL;
import static trackup.logic.parser.CliSyntax.PREFIX_NAME;
import static trackup.logic.parser.CliSyntax.PREFIX_PHONE;
import static trackup.logic.parser.CliSyntax.PREFIX_TAG;
import static trackup.logic.parser.CommandParserTestUtil.assertParseFailure;
import static trackup.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import trackup.logic.Messages;
import trackup.logic.commands.DeleteByCommand;
import trackup.model.person.Address;
import trackup.model.person.Email;
import trackup.model.person.Name;
import trackup.model.person.Phone;
import trackup.model.tag.Tag;

public class DeleteByCommandParserTest {

    private final DeleteByCommandParser parser = new DeleteByCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        DeleteByCommand expectedCommand = new DeleteByCommand(
                Optional.of(new Name(VALID_NAME_AMY)),
                Optional.of(new Phone(VALID_PHONE_AMY)),
                Optional.of(new Email(VALID_EMAIL_AMY)),
                Optional.of(new Address(VALID_ADDRESS_AMY)),
                Optional.of(new Tag(VALID_TAG_FRIEND))
        );

        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + TAG_DESC_FRIEND, expectedCommand);
    }

    @Test
    public void parse_someFieldsPresent_success() {
        DeleteByCommand expectedCommand = new DeleteByCommand(
                Optional.of(new Name(VALID_NAME_AMY)),
                Optional.empty(),
                Optional.of(new Email(VALID_EMAIL_AMY)),
                Optional.empty(),
                Optional.of(new Tag(VALID_TAG_FRIEND))
        );

        assertParseSuccess(parser, NAME_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND, expectedCommand);
    }

    @Test
    public void parse_noFieldsPresent_failure() {
        assertParseFailure(parser, "",
                DeleteByCommand.MESSAGE_NO_CRITERIA_SPECIFIED);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_AMY + INVALID_PHONE_DESC + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_AMY + PHONE_DESC_AMY + INVALID_EMAIL_DESC + ADDRESS_DESC_AMY
                        + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + INVALID_ADDRESS_DESC
                        + TAG_DESC_FRIEND, Address.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_duplicateFields_failure() {
        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                        + ADDRESS_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, NAME_DESC_AMY + PHONE_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                        + ADDRESS_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + EMAIL_DESC_AMY
                        + ADDRESS_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple addresses
        assertParseFailure(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + ADDRESS_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple tags
        assertParseFailure(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + TAG_DESC_FRIEND + TAG_DESC_FRIEND,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TAG));
    }
}
