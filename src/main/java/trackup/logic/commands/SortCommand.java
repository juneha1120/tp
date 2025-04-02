package trackup.logic.commands;

import static java.util.Objects.requireNonNull;
import static trackup.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static trackup.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static trackup.logic.parser.CliSyntax.PREFIX_EMAIL;
import static trackup.logic.parser.CliSyntax.PREFIX_NAME;
import static trackup.logic.parser.CliSyntax.PREFIX_PHONE;
import static trackup.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Comparator;

import trackup.model.Model;
import trackup.model.person.Person;

/**
 * Sorts all persons in current filtered list displayed by the prefixes.
 * Keyword matching is case-insensitive.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the address book according to given attribute in the input order."
            + " [true]: optional, to indicate a descending order,\n"
            + " if [true] is not indicated then ascending order by default.\n"
            + "Parameters: "
            + PREFIX_NAME + "[true] "
            + PREFIX_PHONE + "[true] "
            + PREFIX_EMAIL + "[true] "
            + PREFIX_ADDRESS + "[true] "
            + PREFIX_TAG + "[true]"
            + PREFIX_CATEGORY + "[true]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "true\n"
            + COMMAND_WORD + " "
            + PREFIX_CATEGORY + "true "
            + PREFIX_PHONE + "\n"
            + COMMAND_WORD + " "
            + PREFIX_TAG + "true "
            + PREFIX_NAME + "true";

    public static final String MESSAGE_SUCCESS = "Sorted all persons";

    private final Comparator<Person> comparator;

    public SortCommand(Comparator<Person> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Person> getComparator() {
        return this.comparator;
    }
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortFilteredPersonList(comparator);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SortCommand otherCommand)) {
            return false;
        }
        return comparator.equals(otherCommand.comparator);
    }
}
