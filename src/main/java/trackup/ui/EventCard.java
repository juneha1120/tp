package trackup.ui;

import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import trackup.model.event.Event;

/**
 * An UI component that displays information of an {@code Event}.
 */
public class EventCard extends UiPart<Region> {

    private static final String FXML = "EventListCard.fxml";

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public final Event event;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label title;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private FlowPane contacts;

    /**
     * Creates an {@code EventCard} with the given {@code Event} and index to display.
     */
    public EventCard(Event event, int displayedIndex) {
        super(FXML);
        this.event = event;

        id.setText(displayedIndex + ". ");
        title.setText(event.getTitle());
        startTime.setText("From: " + event.getStartDateTime().format(formatter));
        endTime.setText("To: " + event.getEndDateTime().format(formatter));

        event.getContacts()
                .forEach(person -> {
                    String labelText = person.getCategory()
                            .map(cat -> "[" + cat.categoryName + "] " + person.getName().fullName)
                            .orElse(person.getName().fullName);

                    String color = person.getCategory()
                            .map(cat -> getCategoryColor(cat.categoryName))
                            .orElse(getCategoryColor(null)); // fallback if no category

                    Label contactLabel = new Label(labelText);
                    contactLabel.setStyle("-fx-background-color: #3B3B3B;"
                            + "-fx-text-fill: " + color + ");"
                            + "-fx-border-radius: 5px; -fx-padding: 1px 2px;");

                    contacts.getChildren().add(contactLabel);
                });

        cardPane.setStyle("-fx-border-color: rgba(240, 240, 240); -fx-border-width: 2px;"
                + "-fx-background-color: rgba(240, 240, 240, 0.1);"); // 10% opacity
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

