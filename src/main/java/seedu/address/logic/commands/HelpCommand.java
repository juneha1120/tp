package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static String SHOWING_HELP_MESSAGE;

    public HelpCommand() {
        SHOWING_HELP_MESSAGE = "Available Commands:\n" +
                "- help: Shows details on available commands\n" +
                "- add: Adds a new contact\n" +
                "- delete: Removes a contact\n" +
                "- list: Displays stored contacts";
    }

    public HelpCommand(String command, String usage, String description) {
        String message = "Command:" + command +
                "\nUsage:" + usage +
                "\nDescription:" + description;
        SHOWING_HELP_MESSAGE = message;
    }

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }
}
