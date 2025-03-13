package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    private final String showing_help_message;

    /**
     * Creates an HelpCommand
     */
    public HelpCommand() {
        this.showing_help_message = "Available Commands:\n"
                + "- help: Shows details on available commands\n"
                + "- add: Adds a new contact\n"
                + "- delete: Removes a contact\n"
                + "- list: Displays stored contacts";
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
        this.showing_help_message = message;
    }

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(showing_help_message, true, false);
    }
}
