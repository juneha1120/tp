package trackup.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static trackup.logic.commands.CommandTestUtil.assertCommandSuccess;
import static trackup.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Comparator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import trackup.model.Model;
import trackup.model.ModelManager;
import trackup.model.UserPrefs;
import trackup.model.person.Person;
import trackup.model.person.comparators.AscendingComparators;
import trackup.model.person.comparators.DescendingComparators;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class SortCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotSorted_showsSameList() {
        assertCommandSuccess(new SortCommand((o1, o2) -> 0), model, SortCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_byName_sortsList() {
        Comparator<Person> comparator = AscendingComparators.NAME_COMPARATOR;
        expectedModel.sortFilteredPersonList(comparator);
        assertCommandSuccess(new SortCommand(comparator), model, SortCommand.MESSAGE_SUCCESS, expectedModel);

        comparator = DescendingComparators.NAME_COMPARATOR;
        expectedModel.sortFilteredPersonList(comparator);
        assertCommandSuccess(new SortCommand(comparator), model, SortCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_byPhone_sortsList() {
        Comparator<Person> comparator = AscendingComparators.PHONE_COMPARATOR;
        expectedModel.sortFilteredPersonList(comparator);
        assertCommandSuccess(new SortCommand(comparator), model, SortCommand.MESSAGE_SUCCESS, expectedModel);

        comparator = DescendingComparators.PHONE_COMPARATOR;
        expectedModel.sortFilteredPersonList(comparator);
        assertCommandSuccess(new SortCommand(comparator), model, SortCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        Comparator<Person> firstComparator = AscendingComparators.NAME_COMPARATOR;
        Comparator<Person> secondComparator = AscendingComparators.PHONE_COMPARATOR;


        SortCommand sortNameCommand = new SortCommand(firstComparator);
        SortCommand sortPhoneCommand = new SortCommand(secondComparator);

        // same object -> returns true
        assertTrue(sortNameCommand.equals(sortNameCommand));

        // same values -> returns true
        SortCommand sortNameCommandCopy = new SortCommand(firstComparator);
        assertTrue(sortNameCommand.equals(sortNameCommandCopy));

        // different types -> returns false
        assertFalse(sortNameCommand.equals(1));

        // null -> returns false
        assertFalse(sortNameCommand.equals(null));

        // different person -> returns false
        assertFalse(sortNameCommand.equals(sortPhoneCommand));
    }
}
