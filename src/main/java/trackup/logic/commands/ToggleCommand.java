package trackup.logic.commands;

import static java.util.Objects.requireNonNull;

import trackup.commons.core.Visibility;
import trackup.logic.Messages;
import trackup.logic.commands.exceptions.CommandException;
import trackup.model.Model;

/**
 * Toggles the visibility of a specific field in the person list UI.
 * <p>
 * The supported fields are:
 * <ul>
 *     <li>{@code name}</li>
 *     <li>{@code phone}</li>
 *     <li>{@code email}</li>
 *     <li>{@code address}</li>
 *     <li>{@code tag}</li>
 *     <li>{@code category}</li>
 * </ul>
 * <p>
 * Executing this command will invert the current visibility of the specified field
 * and trigger a UI re-render via property bindings.
 * <p>
 * Usage example:
 * <pre>{@code
 * toggle name
 * }</pre>
 * This will hide the {@code name} field if it's currently shown, or show it if it's currently hidden.
 */
public class ToggleCommand extends Command {

    public static final String COMMAND_WORD = "toggle";

    public static final String NAME_FIELD_STRING = "name";
    public static final String PHONE_FIELD_STRING = "phone";
    public static final String EMAIL_FIELD_STRING = "email";
    public static final String ADDRESS_FIELD_STRING = "address";
    public static final String TAG_FIELD_STRING = "tag";
    public static final String CATEGORY_FIELD_STRING = "category";
    public static final String NOTE_FIELD_STRING = "note";
    public static final String DATETIME_FIELD_STRING = "datetime";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Toggle the given field in the address book UI. "
            + "Parameters: "
            + "[FIELD PREFIX]\n"
            + "Example: " + COMMAND_WORD + " "
            + NAME_FIELD_STRING;

    public static final String MESSAGE_HIDE_SUCCESS = "Field hidden: %1$s";
    public static final String MESSAGE_UNHIDE_SUCCESS = "Field unhidden: %1$s";

    private final String fieldName;

    /**
     * Constructs a {@code ToggleCommand} to toggle the visibility of the specified field.
     *
     * @param fieldName The name of the field to toggle.
     */
    public ToggleCommand(String fieldName) {
        requireNonNull(fieldName);

        this.fieldName = fieldName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Visibility visibility = model.getGuiSettings().getVisibility();

        CommandResult result = switch (fieldName) {
        case NAME_FIELD_STRING -> {
            visibility.setShowName(!visibility.isShowName());
            yield new CommandResult(String.format(
                    visibility.isShowName() ? MESSAGE_UNHIDE_SUCCESS : MESSAGE_HIDE_SUCCESS, fieldName));
        }
        case PHONE_FIELD_STRING -> {
            visibility.setShowPhone(!visibility.isShowPhone());
            yield new CommandResult(String.format(
                    visibility.isShowPhone() ? MESSAGE_UNHIDE_SUCCESS : MESSAGE_HIDE_SUCCESS, fieldName));
        }
        case EMAIL_FIELD_STRING -> {
            visibility.setShowEmail(!visibility.isShowEmail());
            yield new CommandResult(String.format(
                    visibility.isShowEmail() ? MESSAGE_UNHIDE_SUCCESS : MESSAGE_HIDE_SUCCESS, fieldName));
        }
        case ADDRESS_FIELD_STRING -> {
            visibility.setShowAddress(!visibility.isShowAddress());
            yield new CommandResult(String.format(
                    visibility.isShowAddress() ? MESSAGE_UNHIDE_SUCCESS : MESSAGE_HIDE_SUCCESS, fieldName));
        }
        case TAG_FIELD_STRING -> {
            visibility.setShowTag(!visibility.isShowTag());
            yield new CommandResult(String.format(
                    visibility.isShowTag() ? MESSAGE_UNHIDE_SUCCESS : MESSAGE_HIDE_SUCCESS, fieldName));
        }
        case CATEGORY_FIELD_STRING -> {
            visibility.setShowCategory(!visibility.isShowCategory());
            yield new CommandResult(String.format(
                    visibility.isShowCategory() ? MESSAGE_UNHIDE_SUCCESS : MESSAGE_HIDE_SUCCESS, fieldName));
        }
        case NOTE_FIELD_STRING -> {
            visibility.setShowNote(!visibility.isShowNote());
            yield new CommandResult(String.format(
                    visibility.isShowNote() ? MESSAGE_UNHIDE_SUCCESS : MESSAGE_HIDE_SUCCESS, fieldName));
        }
        case DATETIME_FIELD_STRING -> {
            visibility.setShowDatetime(!visibility.isShowDatetime());
            yield new CommandResult(String.format(
                    visibility.isShowDatetime() ? MESSAGE_UNHIDE_SUCCESS : MESSAGE_HIDE_SUCCESS, fieldName));
        }
        default -> throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
        };


        // Render the UI component
        return result;
    }

    /**
     * Checks whether another object is equal to this {@code ToggleCommand}.
     * Two {@code ToggleCommand} objects are considered equal if they toggle the same field.
     *
     * @param other the other object to compare to
     * @return true if the other object is a {@code ToggleCommand} with the same field name; false otherwise
     */
    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (this == other) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ToggleCommand otherCommand)) {
            return false;
        }

        // state check
        return this.fieldName.equals(otherCommand.fieldName);
    }
}
