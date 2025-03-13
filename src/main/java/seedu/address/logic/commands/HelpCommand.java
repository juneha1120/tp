package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Available Commands:\n"
            + "- help: Shows details on available commands\n"
            + "- add: Adds a new contact\n"
            + "- delete: Removes a contact\n"
            + "- list: Displays stored contacts";

    private String SHOWING_COMMAND_MESSAGE = "";
    /**
     * Creates an HelpCommand
     */
    public HelpCommand() {
    }

    /**
     * Creates an HelpCommand according to the specified {@code Command}'s
     * @param command {@code COMMAND_WORD} of the command
     * @param usage {@code COMMAND_USAGE} of the command
     * @param description the description of the command
     */
    public HelpCommand(String command, String usage, String description) {
        String message = "Command:" + command
                + "\nUsage:" + usage
                + "\nDescription:" + description;
        this.SHOWING_COMMAND_MESSAGE = message;
    }

    @Override
    public CommandResult execute(Model model) {
        if (!SHOWING_COMMAND_MESSAGE.isEmpty()) {
            return new CommandResult(SHOWING_COMMAND_MESSAGE, true, false);
        }
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }
}
