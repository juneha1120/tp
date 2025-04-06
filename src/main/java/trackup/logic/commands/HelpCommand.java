package trackup.logic.commands;

import trackup.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows a full usage of given command with examples.\n"
            + "Parameter(s): "
            + "[<COMMAND_WORD>]\n"
            + "Example: " + COMMAND_WORD + " add";

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.\n";

    private final String specificCommandUsage;

    /**
     * Creates an HelpCommand
     */
    public HelpCommand() {
        this.specificCommandUsage = "";
    }

    /**
     * Creates an HelpCommand according to the specified {@code Command}'s
     * @param command {@code COMMAND_WORD} of the command
     * @param usage {@code COMMAND_USAGE} of the command
     */
    public HelpCommand(String command, String usage) {
        this.specificCommandUsage = usage;
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
        return specificCommandUsage.equals(otherFindCommand.specificCommandUsage);
    }

    @Override
    public CommandResult execute(Model model) {
        if (!specificCommandUsage.isEmpty()) {
            return new CommandResult(specificCommandUsage, false, false);
        }
        return new CommandResult(SHOWING_HELP_MESSAGE + MESSAGE_USAGE, true, false);
    }
}
