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

    private final String showingCommandMessage;
    /**
     * Creates an HelpCommand
     */
    public HelpCommand() {
        this.showingCommandMessage = "";
    }

    /**
     * Creates an HelpCommand according to the specified {@code Command}'s
     * @param command {@code COMMAND_WORD} of the command
     * @param usage {@code COMMAND_USAGE} of the command
     * @param description the description of the command
     */
    public HelpCommand(String command, String usage, String description) {
        String message = "Usage:" + usage + "\nDescription:" + description;
        this.showingCommandMessage = message;
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof HelpCommand)) {
            return false;
        }

        HelpCommand otherFindCommand = (HelpCommand) other;
        return showingCommandMessage.equals(otherFindCommand.showingCommandMessage);
    }

    @Override
    public CommandResult execute(Model model) {
        if (!showingCommandMessage.isEmpty()) {
            return new CommandResult(showingCommandMessage, false, false);
        }
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }
}
