package trackup.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static trackup.logic.commands.CommandTestUtil.assertCommandSuccess;
import static trackup.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import trackup.commons.core.Visibility;
import trackup.logic.commands.exceptions.CommandException;
import trackup.model.Model;
import trackup.model.ModelManager;
import trackup.model.UserPrefs;

public class ToggleCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_toggleNameField_togglesSuccessfully() throws CommandException {
        boolean originalVisibility = model.getGuiSettings().getVisibility().isShowName();

        ToggleCommand command = new ToggleCommand(ToggleCommand.NAME_FIELD_STRING);
        String expectedMessage = String.format(
                originalVisibility ? ToggleCommand.MESSAGE_HIDE_SUCCESS : ToggleCommand.MESSAGE_UNHIDE_SUCCESS,
                ToggleCommand.NAME_FIELD_STRING);

        assertCommandSuccess(command, model, expectedMessage, model);
        assertTrue(model.getGuiSettings().getVisibility().isShowName() != originalVisibility);
    }

    @Test
    public void execute_toggleAllFields_success() {
        assertToggleField(ToggleCommand.NAME_FIELD_STRING);
        assertToggleField(ToggleCommand.PHONE_FIELD_STRING);
        assertToggleField(ToggleCommand.EMAIL_FIELD_STRING);
        assertToggleField(ToggleCommand.ADDRESS_FIELD_STRING);
        assertToggleField(ToggleCommand.TAG_FIELD_STRING);
        assertToggleField(ToggleCommand.CATEGORY_FIELD_STRING);
    }

    private void assertToggleField(String field) {
        boolean original = getVisibilityForField(model.getGuiSettings().getVisibility(), field);

        ToggleCommand command = new ToggleCommand(field);
        String expectedMessage = String.format(
                original ? ToggleCommand.MESSAGE_HIDE_SUCCESS : ToggleCommand.MESSAGE_UNHIDE_SUCCESS, field);

        assertCommandSuccess(command, model, expectedMessage, model);

        boolean updated = getVisibilityForField(model.getGuiSettings().getVisibility(), field);
        assertTrue(updated != original);
    }

    private boolean getVisibilityForField(Visibility visibility, String field) {
        return switch (field) {
        case ToggleCommand.NAME_FIELD_STRING -> visibility.isShowName();
        case ToggleCommand.PHONE_FIELD_STRING -> visibility.isShowPhone();
        case ToggleCommand.EMAIL_FIELD_STRING -> visibility.isShowEmail();
        case ToggleCommand.ADDRESS_FIELD_STRING -> visibility.isShowAddress();
        case ToggleCommand.TAG_FIELD_STRING -> visibility.isShowTag();
        case ToggleCommand.CATEGORY_FIELD_STRING -> visibility.isShowCategory();
        default -> throw new IllegalArgumentException("Unknown field: " + field);
        };
    }

    @Test
    public void execute_invalidField_throwsCommandException() {
        ToggleCommand command = new ToggleCommand("invalidField");
        try {
            command.execute(model);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Invalid command format"));
        }
    }

    @Test
    public void equals() {
        ToggleCommand toggleName = new ToggleCommand("name");
        ToggleCommand togglePhone = new ToggleCommand("phone");

        // same object -> returns true
        assertTrue(toggleName.equals(toggleName));

        // same values -> returns true
        ToggleCommand toggleNameCopy = new ToggleCommand("name");
        assertTrue(toggleName.equals(toggleNameCopy));

        // different types -> returns false
        assertFalse(toggleName.equals(1));

        // null -> returns false
        assertFalse(toggleName.equals(null));

        // different field -> returns false
        assertFalse(toggleName.equals(togglePhone));
    }
}
