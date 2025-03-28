package trackup.logic.parser;

import static org.junit.jupiter.api.Assertions.*;
import static trackup.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static trackup.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static trackup.logic.parser.CliSyntax.PREFIX_EMAIL;
import static trackup.logic.parser.CliSyntax.PREFIX_NAME;
import static trackup.logic.parser.CliSyntax.PREFIX_PHONE;
import static trackup.logic.parser.CliSyntax.PREFIX_TAG;

import org.junit.jupiter.api.Test;

import trackup.logic.commands.SortCommand;
import trackup.logic.parser.exceptions.ParseException;
import trackup.model.person.Person;
import trackup.model.person.comparators.AscendingComparators;
import trackup.model.person.comparators.DescendingComparators;
import trackup.testutil.TypicalPersons;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class SortCommandParserTest {

    private final SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_validSingleFieldAscending_success() throws ParseException {
        // Test single field ascending sort
        SortCommand command = parser.parse(" " + PREFIX_NAME + "false");
        assertEquals(new SortCommand(AscendingComparators.NAME_COMPARATOR), command);
    }

    @Test
    public void parse_validSingleFieldDescending_success() throws ParseException {
        // Test single field descending sort
        SortCommand command = parser.parse(" " + PREFIX_EMAIL + "true");
        assertEquals(new SortCommand(DescendingComparators.EMAIL_COMPARATOR), command);
    }

    @Test
    public void parse_validMultipleFields_success() throws ParseException {
        // Test multiple fields sort with correct order
        SortCommand command = parser.parse(" " + PREFIX_NAME + "false " + PREFIX_EMAIL + "true");
        Comparator<Person> expected = AscendingComparators.NAME_COMPARATOR
                .thenComparing(DescendingComparators.EMAIL_COMPARATOR);
        assertComparatorEquals(expected, command.getComparator());;
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        // Test empty input
        assertThrows(ParseException.class, () -> parser.parse(""));
    }

    @Test
    public void parse_missingBooleanValue_throwsParseException() throws ParseException {
        // Test missing boolean value
        Comparator<?> expected = AscendingComparators.NAME_COMPARATOR;
        SortCommand command = parser.parse(" " + PREFIX_NAME);
        assertEquals(expected, command.getComparator());
    }

    @Test
    public void parse_unknownPrefix_throwsParseException() {
        // Test unknown prefix
        assertThrows(ParseException.class, () -> parser.parse(" " + "unknown/" + "true"));
    }

    @Test
    public void parse_allFields_success() throws ParseException {
        // Test all possible fields
        String args = String.join(" ",
                PREFIX_NAME + "false",
                PREFIX_PHONE + "true",
                PREFIX_EMAIL + "false",
                PREFIX_ADDRESS + "true",
                PREFIX_TAG + "false",
                PREFIX_CATEGORY + "true");

        SortCommand command = parser.parse(" " + args);
        Comparator<Person> expected = AscendingComparators.NAME_COMPARATOR
                .thenComparing(DescendingComparators.PHONE_COMPARATOR)
                .thenComparing(AscendingComparators.EMAIL_COMPARATOR)
                .thenComparing(DescendingComparators.ADDRESS_COMPARATOR)
                .thenComparing(AscendingComparators.TAG_COMPARATOR)
                .thenComparing(DescendingComparators.CATEGORY_COMPARATOR);
        assertComparatorEquals(expected, command.getComparator());
    }

    @Test
    public void parse_fieldsInDifferentOrder_success() throws ParseException {
        // Test fields in different order than declaration
        SortCommand command = parser.parse(" " + PREFIX_EMAIL + "false " + PREFIX_NAME + "true");
        Comparator<Person> expected = AscendingComparators.EMAIL_COMPARATOR
                .thenComparing(DescendingComparators.NAME_COMPARATOR);
        assertComparatorEquals(expected, command.getComparator());
    }

    private void assertComparatorEquals(Comparator<Person> expected, Comparator<Person> actual) {
        List<Person> persons = TypicalPersons.getTypicalPersons();
        assertStreamEquals(persons.stream().sorted(expected), persons.stream().sorted(actual));
    }

    static void assertStreamEquals(Stream<?> s1, Stream<?> s2) {
        Iterator<?> iter1 = s1.iterator(), iter2 = s2.iterator();
        while(iter1.hasNext() && iter2.hasNext())
            assertEquals(iter1.next(), iter2.next());
        assert !iter1.hasNext() && !iter2.hasNext();
    }
}