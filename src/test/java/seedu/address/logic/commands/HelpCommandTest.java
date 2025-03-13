package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        String help_message = "Available Commands:\n"
                + "- help: Shows details on available commands\n"
                + "- add: Adds a new contact\n"
                + "- delete: Removes a contact\n"
                + "- list: Displays stored contacts";
        CommandResult expectedCommandResult = new CommandResult(help_message, true, false);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }
}
