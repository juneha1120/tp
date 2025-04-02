package trackup.ui;

import java.util.Comparator;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import trackup.commons.core.Visibility;
import trackup.model.note.Note;
import trackup.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label category;

    @FXML
    private VBox notesBox;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex, Visibility visibility) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        id.setVisible(visibility.isShowId());
        name.setText(person.getName().fullName);
        name.setVisible(visibility.isShowName());
        phone.setText(person.getPhone().value);
        phone.setVisible(visibility.isShowPhone());
        address.setText(person.getAddress().value);
        address.setVisible(visibility.isShowAddress());
        email.setText(person.getEmail().value);
        email.setVisible(visibility.isShowEmail());
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> {
                    Label label = new Label(tag.tagName);
                    label.setVisible(visibility.isShowTag());
                    tags.getChildren().add(label);
                });
        if (person.getCategory().isPresent()) {
            String categoryName = person.getCategory().get().categoryName;
            category.setText(categoryName);
            category.setVisible(visibility.isShowCategory());
            String categoryColor = getCategoryColor(categoryName);
            category.setStyle("-fx-background-color: #3B3B3B;"
                    + "-fx-text-fill: " + categoryColor + "); -fx-border-radius: 5px; -fx-padding: 1px 2px;");
            cardPane.setStyle("-fx-border-color: " + categoryColor + ");" + "-fx-border-width: 2px;"
                    + "-fx-background-color: " + categoryColor + ", 0.1);"); // 10% opacity
        } else {
            category.setVisible(false);
            category.setManaged(false);
            String categoryColor = getCategoryColor(null);
            category.setStyle("-fx-background-color: #3B3B3B;"
                    + "-fx-text-fill: " + categoryColor + "); -fx-border-radius: 5px; -fx-padding: 1px 2px;");
            cardPane.setStyle("-fx-border-color: " + categoryColor + ");" + "-fx-border-width: 2px;"
                    + "-fx-background-color: " + categoryColor + ", 0.1);"); // 10% opacity
        }
        if (visibility.isShowNote()) {
            renderNotes();
            person.getNotes().addListener((ListChangeListener<Note>) change -> renderNotes());
        }
    }

    private void renderNotes() {
        notesBox.getChildren().clear();

        person.getNotes().stream().limit(Person.MAX_NOTES).forEach(note -> {
            Label noteLabel = new Label(note.text);
            noteLabel.setWrapText(true);
            noteLabel.setTextOverrun(OverrunStyle.ELLIPSIS);
            noteLabel.setMaxWidth(250);
            noteLabel.getStyleClass().add("note-label");
            notesBox.getChildren().add(noteLabel);
        });
    }

    public String getCategoryColor(String categoryName) {
        if (categoryName == null) {
            return "rgba(240, 240, 240";
        }

        return switch (categoryName) {
        case "Client" -> "rgba(255, 221, 193"; // Light Orange
        case "Investor" -> "rgba(193, 255, 215"; // Light Green
        case "Partner" -> "rgba(193, 212, 255"; // Light Blue
        case "Other" -> "rgba(224, 193, 255"; // Light Purple
        default -> "rgba(240, 240, 240"; // Default Gray
        };
    }
}
