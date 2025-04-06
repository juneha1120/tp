package trackup.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static trackup.logic.commands.CommandTestUtil.assertCommandSuccess;
import static trackup.logic.commands.HelpCommand.MESSAGE_USAGE;
import static trackup.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import trackup.model.Model;
import trackup.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE + MESSAGE_USAGE, true, false);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }
    @Test
    public void execute_helpAdd_success() {
        CommandResult expectedCommandResult = new CommandResult(AddCommand.MESSAGE_USAGE, false, false);
        assertCommandSuccess(new HelpCommand(AddCommand.MESSAGE_USAGE),
                model, expectedCommandResult, expectedModel);
    }

    @Test
    public void equals() {
        HelpCommand firstHelpCommand = new HelpCommand("add n/NAME");
        HelpCommand secondHelpCommand = new HelpCommand("add n/NAME");
        HelpCommand thirdHelpCommand = new HelpCommand("delete INDEX");

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
