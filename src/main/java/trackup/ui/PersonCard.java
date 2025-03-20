package trackup.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
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

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        if (person.getCategory().isPresent()) {
            String categoryName = person.getCategory().get().categoryName;
            category.setText(categoryName);
            getCategoryColor(categoryName);
        } else {
            category.setVisible(false);
            category.setManaged(false);
            getCategoryColor(null);
        }
    }

    public void getCategoryColor(String categoryName) {
        if (categoryName == null) {
            cardPane.setStyle("-fx-border-color: rgba(240, 240, 240);" + "-fx-border-width: 2px;"
                    + "-fx-background-color: rgba(240, 240, 240, 0.1);"); // 10% opacity
        } else {
            // Apply different border colors based on categoryName
            String categoryColor = switch (categoryName) {
            case "Client" -> "rgba(255, 221, 193"; // Light Orange
            case "Investor" -> "rgba(193, 255, 215"; // Light Green
            case "Partner" -> "rgba(193, 212, 255"; // Light Blue
            case "Other" -> "rgba(224, 193, 255"; // Light Purple
            default -> "rgba(240, 240, 240"; // Default Gray
            };
            cardPane.setStyle("-fx-border-color: " + categoryColor + ");" + "-fx-border-width: 2px;"
                    + "-fx-background-color: " + categoryColor + ", 0.1);"); // 10% opacity
        }
    }
}
