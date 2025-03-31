package trackup.ui;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;

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

        event.getContacts().stream()
                .sorted(Comparator.comparing(person -> person.getName().fullName))
                .forEach(person -> {
                    Label contactLabel = new Label(person.getName().fullName);
                    contacts.getChildren().add(contactLabel);
                });

        cardPane.setStyle("-fx-border-color: rgba(240, 240, 240); -fx-border-width: 2px;"
                + "-fx-background-color: rgba(240, 240, 240, 0.1);"); // 10% opacity
    }
}

