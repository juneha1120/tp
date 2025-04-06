package trackup.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;
import java.util.stream.Collectors;

import trackup.model.Model;
import trackup.model.person.Person;
import trackup.model.tag.Tag;

/**
 * Finds and lists all persons in the address book whose attributes contain the specified keyword.
 */
public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds persons whose attributes contain the given keyword.\n"
            + "Parameter(s): "
            + "<KEYWORD>\n"
            + "Example: " + COMMAND_WORD + " John";

    public static final String MESSAGE_SUCCESS = "Listed persons matching: %1$s";
    public static final String MESSAGE_NO_MATCH = "No matching person found with: %1$s";

    private final String keyword;

    /**
     * Constructs a SearchCommand with the specified keyword.
     *
     * @param keyword The keyword to search for in person attributes.
     */
    public SearchCommand(String keyword) {
        requireNonNull(keyword);
        this.keyword = keyword;
    }

    /**
     * Executes the search command by filtering the list of persons whose attributes contain the keyword.
     *
     * @param model The model containing the person list.
     * @return A CommandResult indicating the search results.
     */
    @Override
    public CommandResult execute(Model model) {
        String lowerKeyword = this.keyword.toLowerCase();
        requireNonNull(model);
        Predicate<Person> matchesKeyword = person ->
                person.getName().fullName.toLowerCase().contains(lowerKeyword)
                        || person.getPhone().value.contains(lowerKeyword)
                        || person.getEmail().value.toLowerCase().contains(lowerKeyword)
                        || person.getAddress().value.toLowerCase().contains(lowerKeyword)
                        || person.getTags().stream().map(Tag::toString).collect(Collectors.joining(" "))
                        .toLowerCase().contains(lowerKeyword)
                        || (person.getCategory().isPresent() && person.getCategory().get().toString().toLowerCase()
                        .contains(lowerKeyword));

        model.updateFilteredPersonList(matchesKeyword);

        if (model.getFilteredPersonList().isEmpty()) {
            return new CommandResult(String.format(MESSAGE_NO_MATCH, keyword));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, keyword));
    }

    /**
     * Checks if this SearchCommand is equal to another object.
     *
     * @param other The object to compare.
     * @return True if the object is an instance of SearchCommand with the same keyword, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SearchCommand)) {
            return false;
        }
        SearchCommand otherCommand = (SearchCommand) other;
        return keyword.equals(otherCommand.keyword);
    }
}
