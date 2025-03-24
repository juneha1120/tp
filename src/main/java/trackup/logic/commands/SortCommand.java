package trackup.logic.commands;

import trackup.model.Model;
import trackup.model.person.Person;

import java.util.Comparator;

import static java.util.Objects.requireNonNull;

public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " -n "
            + "Example: " + COMMAND_WORD + " -n ";

    public static final String MESSAGE_SUCCESS = "Sorted all persons";

    private final Comparator<Person> comparator;

    public SortCommand(Comparator<Person> comparator) {
        this.comparator = comparator;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortPerson(comparator);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SearchCommand)) {
            return false;
        }
        SortCommand otherCommand = (SortCommand) other;
        return comparator.equals(otherCommand.comparator);
    }
}
