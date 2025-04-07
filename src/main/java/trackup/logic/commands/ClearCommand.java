package trackup.logic.commands;

import static java.util.Objects.requireNonNull;

import trackup.model.AddressBook;
import trackup.model.Model;

/**
 * Clears TrackUp's contacts.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clears all contacts and events from TrackUp.\n";
    public static final String MESSAGE_SUCCESS = "TrackUp's contacts and events lists have been cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
