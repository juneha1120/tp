package trackup.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import trackup.model.Model;
import trackup.model.category.Category;

/**
 * Lists all persons in the address book, optionally filtering by category.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays all contacts in TrackUp, optionally filtering by category.\n"
            + "Parameter(s): "
            + "[<CATEGORY>]\n"
            + "Example: " + COMMAND_WORD + " "
            + "Client";

    public static final String MESSAGE_SUCCESS = "Listed all persons";
    public static final String MESSAGE_SUCCESS_FILTERED = "Listed all persons in category: %1$s";

    private final Optional<Category> category;

    public ListCommand(Optional<Category> category) {
        this.category = category;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (category.isPresent()) {
            Category filterCategory = category.get();
            model.updateFilteredPersonList(person -> person.hasCategory(filterCategory));
            return new CommandResult(String.format(MESSAGE_SUCCESS_FILTERED, filterCategory));
        } else {
            model.updateFilteredPersonList(person -> true);
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ListCommand)) {
            return false;
        }
        ListCommand otherCommand = (ListCommand) other;
        return category.equals(otherCommand.category);
    }
}
