package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for SearchCommand.
 */
public class SearchCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_searchByName_personFound() {
        SearchCommand searchCommand = new SearchCommand(ALICE.getName().fullName);

        expectedModel.updateFilteredPersonList(p -> p.getName().fullName.toLowerCase()
                .contains(ALICE.getName().fullName.toLowerCase()));

        assertCommandSuccess(searchCommand, model,
                String.format(SearchCommand.MESSAGE_SUCCESS, ALICE.getName().fullName), expectedModel);
    }

    @Test
    public void execute_searchByPhone_personFound() {
        SearchCommand searchCommand = new SearchCommand(ALICE.getPhone().value);

        expectedModel.updateFilteredPersonList(p -> p.getPhone().value.contains(ALICE.getPhone().value));

        assertCommandSuccess(searchCommand, model,
                String.format(SearchCommand.MESSAGE_SUCCESS, ALICE.getPhone().value), expectedModel);
    }

    @Test
    public void execute_searchByEmail_personFound() {
        SearchCommand searchCommand = new SearchCommand(ALICE.getEmail().value);

        expectedModel.updateFilteredPersonList(p -> p.getEmail().value.toLowerCase()
                .contains(ALICE.getEmail().value.toLowerCase()));

        assertCommandSuccess(searchCommand, model,
                String.format(SearchCommand.MESSAGE_SUCCESS, ALICE.getEmail().value), expectedModel);
    }

    @Test
    public void execute_searchByAddress_personFound() {
        SearchCommand searchCommand = new SearchCommand(ALICE.getAddress().value);

        expectedModel.updateFilteredPersonList(p -> p.getAddress().value.toLowerCase()
                .contains(ALICE.getAddress().value.toLowerCase()));

        assertCommandSuccess(searchCommand, model,
                String.format(SearchCommand.MESSAGE_SUCCESS, ALICE.getAddress().value), expectedModel);
    }

    @Test
    public void execute_searchByTag_personFound() {
        String tagKeyword = ALICE.getTags().iterator().next().toString(); // Get first tag
        SearchCommand searchCommand = new SearchCommand(tagKeyword);

        expectedModel.updateFilteredPersonList(p -> p.getTags().stream().anyMatch(tag ->
                tag.toString().toLowerCase().contains(tagKeyword.toLowerCase())));

        assertCommandSuccess(searchCommand, model,
                String.format(SearchCommand.MESSAGE_SUCCESS, tagKeyword), expectedModel);
    }

    @Test
    public void execute_searchByCategory_personFound() {
        String categoryKeyword = ALICE.getCategory().get().toString(); // Get category
        SearchCommand searchCommand = new SearchCommand(categoryKeyword);

        expectedModel.updateFilteredPersonList(p ->
                p.getCategory().isPresent() && p.getCategory().get().toString().toLowerCase()
                        .contains(categoryKeyword.toLowerCase()));

        assertCommandSuccess(searchCommand, model,
                String.format(SearchCommand.MESSAGE_SUCCESS, categoryKeyword), expectedModel);
    }

    @Test
    public void execute_searchPersonNotFound() {
        SearchCommand searchCommand = new SearchCommand("Nonexistent Keyword");

        expectedModel.updateFilteredPersonList(p -> false); // No matches

        assertCommandSuccess(searchCommand, model,
                String.format(SearchCommand.MESSAGE_SUCCESS, "Nonexistent Keyword"), expectedModel);
    }

    @Test
    public void equals() {
        SearchCommand searchAliceCommand = new SearchCommand("Alice");
        SearchCommand searchBobCommand = new SearchCommand("Bob");

        // Same object -> returns true
        assertTrue(searchAliceCommand.equals(searchAliceCommand));

        // Same values -> returns true
        SearchCommand searchAliceCommandCopy = new SearchCommand("Alice");
        assertTrue(searchAliceCommand.equals(searchAliceCommandCopy));

        // Different keyword -> returns false
        assertFalse(searchAliceCommand.equals(searchBobCommand));

        // Different types -> returns false
        assertFalse(searchAliceCommand.equals(1));

        // Null -> returns false
        assertFalse(searchAliceCommand.equals(null));
    }
}
