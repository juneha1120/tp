package trackup.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static trackup.logic.commands.CommandTestUtil.assertCommandSuccess;
import static trackup.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import trackup.model.Model;
import trackup.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_specificCommand_returnsCommandUsage() {
        String commandWord = "add";
        String usage = "add NAME PHONE EMAIL ADDRESS TAG...";
        String description = "Adds a new contact to the address book.";

        HelpCommand helpCommand = new HelpCommand(commandWord, usage, description);
        CommandResult result = helpCommand.execute(model);

        String expectedMessage = "Usage:" + usage + "\nDescription:" + description;
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertFalse(result.isShowHelp());
        assertFalse(result.isExit());
    }

    @Test
    public void equals() {
        HelpCommand firstHelpCommand = new HelpCommand("add", "add n/NAME", "Adds a new contact");
        HelpCommand secondHelpCommand = new HelpCommand("add", "add n/NAME", "Adds a new contact");
        HelpCommand thirdHelpCommand = new HelpCommand("delete", "delete INDEX", "Deletes a contact");

        // same object -> returns true
        assertTrue(firstHelpCommand.equals(firstHelpCommand));

        // same values -> returns true
        assertTrue(firstHelpCommand.equals(secondHelpCommand));

        // different types -> returns false
        assertFalse(firstHelpCommand.equals(1));

        // null -> returns false
        assertFalse(firstHelpCommand.equals(null));

        // different command details -> returns false
        assertFalse(firstHelpCommand.equals(thirdHelpCommand));
    }
}
