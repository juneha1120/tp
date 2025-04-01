package trackup.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import trackup.commons.core.index.Index;
import trackup.commons.util.StringUtil;
import trackup.logic.parser.exceptions.ParseException;
import trackup.model.category.Category;
import trackup.model.person.Address;
import trackup.model.person.Email;
import trackup.model.person.Name;
import trackup.model.person.Phone;
import trackup.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_DATE_TIME = "Invalid date-time format! Use yyyy-MM-dd HH:mm.";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // Add this assertion to ensure formatter is not null (defensive)
    static {
        assert DATE_TIME_FORMATTER != null : "DATE_TIME_FORMATTER should have been initialized";
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();

        assert trimmedIndex != null : "Trimmed index should not be null";
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }

        int index = Integer.parseInt(trimmedIndex);
        assert index > 0 : "Parsed index must be greater than 0";

        return Index.fromOneBased(index);
    }


    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        assert !trimmedName.isEmpty() : "Name should not be empty after trimming";

        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }


    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();

        assert !trimmedPhone.isEmpty() : "Phone should not be empty after trimming";

        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }

        return new Phone(trimmedPhone);
    }


    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();

        assert !trimmedAddress.isEmpty() : "Address should not be empty after trimming";

        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }

        return new Address(trimmedAddress);
    }


    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();

        assert !trimmedEmail.isEmpty() : "Email should not be empty after trimming";

        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }

        return new Email(trimmedEmail);
    }


    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();

        assert !trimmedTag.isEmpty() : "Tag should not be empty after trimming";

        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }

        return new Tag(trimmedTag);
    }


    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            Tag tag = parseTag(tagName);
            assert tag != null : "Parsed tag should not be null";
            tagSet.add(tag);
        }
        return tagSet;
    }


    /**
     * Parses a {@code String category} into a {@code Category}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code category} is invalid.
     */
    public static Category parseCategory(String category) throws ParseException {
        requireNonNull(category);

        if (category.isEmpty()) {
            return null;
        }

        String formattedCategory = category.trim().substring(0, 1).toUpperCase()
                + category.trim().substring(1).toLowerCase();

        assert !formattedCategory.isEmpty() : "Formatted category should not be empty";

        if (!Category.isValidCategoryName(formattedCategory)) {
            throw new ParseException(Category.MESSAGE_CONSTRAINTS);
        }

        return new Category(formattedCategory);
    }


    /**
     * Parses a {@code String eventName} into a valid event title.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code eventName} is empty.
     */
    public static String parseEventTitle(String eventTitle) throws ParseException {
        requireNonNull(eventTitle);
        String trimmedTitle = eventTitle.trim();

        assert !trimmedTitle.isEmpty() : "Event title should not be empty after trimming";

        if (trimmedTitle.isEmpty()) {
            throw new ParseException("Event title cannot be empty.");
        }

        return trimmedTitle;
    }


    /**
     * Parses a {@code String dateTime} into a {@code LocalDateTime}.
     * Expected format: "yyyy-MM-dd HH:mm".
     *
     * @throws ParseException if the given {@code dateTime} is invalid.
     */
    public static LocalDateTime parseEventTime(String dateTime) throws ParseException {
        requireNonNull(dateTime);
        String trimmedDateTime = dateTime.trim();
        assert !trimmedDateTime.isEmpty() : "Date-time string should not be empty";

        try {
            return LocalDateTime.parse(trimmedDateTime, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new ParseException(MESSAGE_INVALID_DATE_TIME);
        }
    }


    /**
     * Parses a {@code Collection<String> indexes} into a {@code Set<Index>}.
     * @throws ParseException if any index is invalid.
     */
    public static Set<Index> parseContacts(Collection<String> indexes) throws ParseException {
        requireNonNull(indexes);
        final Set<Index> indexSet = new HashSet<>();

        for (String indexStr : indexes) {
            Index index = parseIndex(indexStr);
            assert index.getZeroBased() >= 0 : "Parsed index should be non-negative";
            indexSet.add(index);
        }
        return indexSet;
    }

}
