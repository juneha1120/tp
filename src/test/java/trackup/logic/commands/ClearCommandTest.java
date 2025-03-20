package trackup.logic.commands;

import static trackup.logic.commands.CommandTestUtil.assertCommandSuccess;
import static trackup.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import trackup.model.AddressBook;
import trackup.model.Model;
import trackup.model.ModelManager;
import trackup.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
