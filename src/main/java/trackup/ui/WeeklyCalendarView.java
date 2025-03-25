package trackup.ui;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import trackup.model.event.Event;

/**
 * Represents a weekly calendar view displaying events from Monday to Sunday.
 * Events are displayed in a grid format where rows represent time slots
 * and columns represent days of the week.
 */
public class WeeklyCalendarView {
    private static final int CELL_WIDTH = 100;
    private static final int CELL_HEIGHT = 50;

    private GridPane calendarGrid;
    private ObservableList<Event> eventList;
    private LocalDate currentWeekStart;

    /**
     * Constructs a {@code WeeklyCalendarView} with the given list of events.
     *
     * @param eventList The list of events to be displayed in the calendar.
     */
    public WeeklyCalendarView(ObservableList<Event> eventList) {
        this.eventList = eventList;
        this.calendarGrid = new GridPane();
        this.currentWeekStart = LocalDate.now().with(DayOfWeek.MONDAY);
    }

    /**
     * Populates the calendar grid with time labels and event labels.
     * Time slots range from 8:00 AM to 6:00 PM, and events are placed in
     * appropriate slots based on their start time.
     */
    public void populateCalendar() {
        calendarGrid.getChildren().clear();
        addDayHeaders();
        addTimeLabels();
        addEventLabels();
    }

    /**
     * Adds the day headers (Monday to Sunday) with corresponding dates.
     */
    private void addDayHeaders() {
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("EEEE");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d");

        for (int i = 1; i <= 7; i++) {
            LocalDate dayDate = currentWeekStart.plusDays(i - 1);

            Label dateLabel = new Label(dayDate.format(dateFormatter));
            dateLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");

            Label dayLabel = new Label(dayDate.format(dayFormatter));
            dayLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

            VBox dayBox = new VBox(5, dateLabel, dayLabel);
            dayBox.setAlignment(Pos.CENTER_LEFT);
            dayBox.setMinSize(CELL_WIDTH, 60);
            dayBox.setMaxSize(CELL_WIDTH, 60);
            dayBox.setStyle("-fx-border-color: derive(#1d1d1d, 10%); -fx-border-width: 1px");

            if (dayDate.equals(LocalDate.now())) {
                dayBox.setStyle("-fx-background-color: #ffcccb; "
                        + "-fx-border-color: derive(#1d1d1d, 10%); -fx-border-width: 1px");
            }

            GridPane.setHalignment(dayBox, HPos.CENTER);
            calendarGrid.add(dayBox, i, 0);
        }
    }

    /**
     * Adds the vertical time labels from 12 AM to 11 PM.
     */
    private void addTimeLabels() {
        for (int i = 0; i < 24; i++) {
            String timeText = (i == 0 ? "12 AM" : (i < 12 ? i + " AM" : (i == 12 ? "12 PM" : (i - 12) + " PM")));

            Label timeLabel = new Label(timeText);
            timeLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold");
            timeLabel.setAlignment(Pos.CENTER);
            for (int j = 0; j <= 7; j++) {
                timeLabel.setMinHeight(getEventStack(j, i).getHeight());
                timeLabel.setMinWidth(40);
                timeLabel.setMaxWidth(40);
            }

            GridPane.setHalignment(timeLabel, HPos.CENTER);
            calendarGrid.add(timeLabel, 0, i + 1);
        }
    }

    /**
     * Adds event labels to the calendar grid.
     */
    private void addEventLabels() {
        for (Event event : eventList) {
            if (isEventInCurrentWeek(event)) {
                int dayColumn = event.getStartDateTime().getDayOfWeek().getValue();
                int startRow = event.getStartDateTime().getHour() + 1;

                VBox eventStack = getEventStack(dayColumn, startRow);

                // Create event label
                Label eventLabel = new Label(event.getTitle());
                eventLabel.setStyle("-fx-background-color: lightblue; -fx-padding: 5px;"
                        + "-fx-border-color: black; -fx-border-width: 1px;"
                        + "-fx-alignment: center; -fx-wrap-text: true;");
                eventLabel.setMinSize(CELL_WIDTH - 2, CELL_HEIGHT - 2);
                eventLabel.setMaxSize(CELL_WIDTH - 2, CELL_HEIGHT - 2);

                // Add event label to stack
                eventStack.getChildren().add(eventLabel);
            }
        }
    }

    /**
     * Checks if an event falls within the current week.
     */
    private boolean isEventInCurrentWeek(Event event) {
        LocalDate eventStartDate = event.getStartDateTime().toLocalDate();
        return !eventStartDate.isBefore(currentWeekStart)
                && !eventStartDate.isAfter(currentWeekStart.plusDays(6));
    }

    /**
     * Gets or creates a VBox for events occurring at the same time and day.
     */
    private VBox getEventStack(int dayColumn, int row) {
        for (Node node : calendarGrid.getChildren()) {
            if (GridPane.getColumnIndex(node) == dayColumn && GridPane.getRowIndex(node) == row) {
                if (node instanceof VBox) {
                    return (VBox) node;
                }
            }
        }

        VBox eventVBox = new VBox(2);
        eventVBox.setAlignment(Pos.TOP_LEFT);
        eventVBox.setMinHeight(CELL_HEIGHT);
        eventVBox.setStyle("-fx-border-color: derive(#1d1d1d, 10%); -fx-border-width: 1px");

        GridPane.setColumnIndex(eventVBox, dayColumn);
        GridPane.setRowIndex(eventVBox, row);

        calendarGrid.getChildren().add(eventVBox);
        return eventVBox;
    }

    /**
     * Moves the calendar view to the previous week and updates the displayed events.
     */
    public void showPreviousWeek() {
        currentWeekStart = currentWeekStart.minusWeeks(1);
        populateCalendar();
    }

    /**
     * Moves the calendar view to the next week and updates the displayed events.
     */
    public void showNextWeek() {
        currentWeekStart = currentWeekStart.plusWeeks(1);
        populateCalendar();
    }

    public LocalDate getCurrentWeekStart() {
        return currentWeekStart;
    }

    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    /**
     * Returns the root node of the calendar grid for integration into the main UI.
     *
     * @return The {@code GridPane} representing the calendar view.
     */
    public GridPane getRoot() {
        return calendarGrid;
    }
}
