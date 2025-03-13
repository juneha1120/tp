package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.category.Category;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(Optional.empty()), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(Optional.empty()), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        Category clientCategory = new Category("Client");
        Category investorCategory = new Category("Investor");

        ListCommand listAllCommand = new ListCommand(Optional.empty());
        ListCommand listClientCommand = new ListCommand(Optional.of(clientCategory));
        ListCommand listSupplierCommand = new ListCommand(Optional.of(investorCategory));

        // same object -> returns true
        assertTrue(listAllCommand.equals(listAllCommand));
        assertTrue(listClientCommand.equals(listClientCommand));

        // same values -> returns true
        ListCommand listClientCommandCopy = new ListCommand(Optional.of(clientCategory));
        assertTrue(listClientCommand.equals(listClientCommandCopy));

        // different types -> returns false
        assertFalse(listClientCommand.equals(1));

        // null -> returns false
        assertFalse(listClientCommand.equals(null));

        // different category -> returns false
        assertFalse(listClientCommand.equals(listSupplierCommand));

        // empty vs non-empty category -> returns false
        assertFalse(listAllCommand.equals(listClientCommand));
    }
}
