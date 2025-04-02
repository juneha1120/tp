package trackup.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static trackup.logic.parser.ParserUtil.MESSAGE_INVALID_DATE_TIME;
import static trackup.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static trackup.testutil.Assert.assertThrows;
import static trackup.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import trackup.logic.parser.exceptions.ParseException;
import trackup.model.category.Category;
import trackup.model.person.Address;
import trackup.model.person.Email;
import trackup.model.person.Name;
import trackup.model.person.Phone;
import trackup.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_DATETIME = "not-a-date";
    private static final String INVALID_CATEGORY = "randomCat";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_DATETIME = "2025-04-01 14:00";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseTags_validTags_assertNotNullHit() throws Exception {
        // Directly hits the path with valid tags
        Set<Tag> tags = ParserUtil.parseTags(Arrays.asList("tech", "biz"));
        assertTrue(tags.contains(new Tag("tech")));
        assertTrue(tags.contains(new Tag("biz")));
    }

    @Test
    public void parseCategory_validCategory_success() throws Exception {
        assertEquals(new Category("Investor"), ParserUtil.parseCategory("investor"));
    }

    @Test
    public void parseCategory_emptyInput_returnsNull() throws Exception {
        assertNull(ParserUtil.parseCategory(""));
    }

    @Test
    public void parseEventTime_validDateTime_success() throws Exception {
        LocalDateTime expected = LocalDateTime.of(2025, 4, 1, 18, 0);
        assertEquals(expected, ParserUtil.parseEventTime("2025-04-01 18:00"));
    }

    @Test
    public void parseEventTime_emptyString_failure() {
        assertThrows(AssertionError.class, () -> ParserUtil.parseEventTime("   "));
    }


    @Test
    public void parseTags_validTags_assertNotNullHit() throws Exception {
        // Directly hits the path with valid tags
        Set<Tag> tags = ParserUtil.parseTags(Arrays.asList("tech", "biz"));
        assertTrue(tags.contains(new Tag("tech")));
        assertTrue(tags.contains(new Tag("biz")));
    }

    @Test
    public void parseCategory_validCategory_success() throws Exception {
        assertEquals(new Category("Investor"), ParserUtil.parseCategory("investor"));
    }

    @Test
    public void parseCategory_emptyInput_returnsNull() throws Exception {
        assertNull(ParserUtil.parseCategory(""));
    }

    @Test
    public void parseEventTime_validDateTime_success() throws Exception {
        LocalDateTime expected = LocalDateTime.of(2025, 4, 1, 18, 0);
        assertEquals(expected, ParserUtil.parseEventTime("2025-04-01 18:00"));
    }

    @Test
    public void parseEventTime_emptyString_failure() {
        assertThrows(AssertionError.class, () -> ParserUtil.parseEventTime("   "));
    }

    @Test
    public void parseEventTitle_valid_returnsTrimmed() throws Exception {
        assertEquals("Meeting", ParserUtil.parseEventTitle("  Meeting  "));
    }

    @Test
    public void parseEventTitle_empty_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEventTitle("   "));
    }

    @Test
    public void parseEventTime_valid_returnsDateTime() throws Exception {
        assertEquals(LocalDateTime.of(2025, 4, 1, 14, 0),
                ParserUtil.parseEventTime(VALID_DATETIME));
    }

    @Test
    public void parseEventTime_invalid_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_DATE_TIME, ()
                -> ParserUtil.parseEventTime(INVALID_DATETIME));
    }

    @Test
    public void parseContacts_valid_returnsIndexSet() throws Exception {
        Set<Integer> expected = Set.of(0, 2);
        Set<Integer> actual = new HashSet<>();
        for (var i : ParserUtil.parseContacts(Arrays.asList("1", "3"))) {
            actual.add(i.getZeroBased());
        }
        assertEquals(expected, actual);
    }

    @Test
    public void parseContacts_invalid_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseContacts(Arrays.asList("1", "a")));
    }

    @Test
    public void parseCategory_valid_returnsCategory() throws Exception {
        Category expected = new Category("Client");
        assertEquals(expected, ParserUtil.parseCategory("client"));
        assertEquals(expected, ParserUtil.parseCategory("Client"));
        assertEquals(expected, ParserUtil.parseCategory("CLIENT"));
    }

    @Test
    public void parseCategory_invalid_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCategory(INVALID_CATEGORY));
    }

    @Test
    public void parseCategory_empty_returnsNull() throws Exception {
        assertEquals(null, ParserUtil.parseCategory(""));
    }

}
