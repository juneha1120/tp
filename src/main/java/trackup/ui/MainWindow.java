package trackup.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import trackup.commons.core.GuiSettings;
import trackup.commons.core.LogsCenter;
import trackup.commons.core.Visibility;
import trackup.logic.Logic;
import trackup.logic.commands.CommandResult;
import trackup.logic.commands.exceptions.CommandException;
import trackup.logic.parser.exceptions.ParseException;
import trackup.model.event.Event;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    private EventListPanel eventListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private WeeklyCalendarView calendarView;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane listPanelStack;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane eventListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private VBox calendarContainer;

    @FXML
    private ScrollPane calendarScrollPane;

    @FXML
    private Label monthYearLabel;

    @FXML
    private Button personsButton;

    @FXML
    private Button eventsButton;

    @FXML
    private Button prevWeekButton;

    @FXML
    private Button nextWeekButton;


    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));

        // F2 → Show Persons
        primaryStage.getScene().getAccelerators().put(
                KeyCombination.valueOf("F2"), () -> personsButton.fire()
        );

        // F3 → Show Events
        primaryStage.getScene().getAccelerators().put(
                KeyCombination.valueOf("F3"), () -> eventsButton.fire()
        );

        // LEFT → Previous Week
        primaryStage.getScene().getAccelerators().put(
                KeyCombination.valueOf("LEFT"), () -> prevWeekButton.fire()
        );

        // RIGHT → Next Week
        primaryStage.getScene().getAccelerators().put(
                KeyCombination.valueOf("RIGHT"), () -> nextWeekButton.fire()
        );

        // Add workaround for TextInputControl consuming function keys
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl) {
                if (KeyCombination.valueOf("F1").match(event)) {
                    helpMenuItem.getOnAction().handle(new ActionEvent());
                    event.consume();
                } else if (KeyCombination.valueOf("F2").match(event)) {
                    personsButton.fire();
                    event.consume();
                } else if (KeyCombination.valueOf("F3").match(event)) {
                    eventsButton.fire();
                    event.consume();
                } else if (KeyCombination.valueOf("LEFT").match(event)) {
                    prevWeekButton.fire();
                    event.consume();
                } else if (KeyCombination.valueOf("RIGHT").match(event)) {
                    nextWeekButton.fire();
                    event.consume();
                }
            }
        });
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        personListPanel = new PersonListPanel(logic.getFilteredPersonList(), logic.getGuiSettings().getVisibility());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        eventListPanel = new EventListPanel(logic.getEventList(), logic.getGuiSettings().getVisibility());
        eventListPanelPlaceholder.getChildren().add(eventListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        ObservableList<Event> eventList = logic.getEventList();
        calendarView = new WeeklyCalendarView(eventList);
        calendarContainer.getChildren().add(calendarView.getRoot());

        // Enable natural scrolling
        calendarScrollPane.setFitToWidth(true);
        calendarScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        calendarScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        calendarView.populateCalendar();
        updateMonthYearLabel(calendarView.getCurrentDate());

        handleShowPersons();
    }

    private void updateMonthYearLabel(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        monthYearLabel.setText(date.format(formatter));
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY(), new Visibility());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
    * @see trackup.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("An error occurred while executing command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        } finally {
            calendarView.populateCalendar();
            updateMonthYearLabel(calendarView.getCurrentDate());
        }
    }

    /**
     * Handles switching to the previous week.
     */
    @FXML
    private void handlePreviousWeek() {
        calendarView.showPreviousWeek();
        updateMonthYearLabel(calendarView.getCurrentWeekStart());
    }

    /**
     * Handles switching to the next week.
     */
    @FXML
    private void handleNextWeek() {
        calendarView.showNextWeek();
        updateMonthYearLabel(calendarView.getCurrentWeekStart());
    }

    /**
     * Handles switching to person list.
     */
    @FXML
    private void handleShowPersons() {
        personListPanelPlaceholder.setVisible(true);
        personListPanelPlaceholder.setManaged(true);
        eventListPanelPlaceholder.setVisible(false);
        eventListPanelPlaceholder.setManaged(false);
    }

    /**
     * Handles switching to event list.
     */
    @FXML
    private void handleShowEvents() {
        personListPanelPlaceholder.setVisible(false);
        personListPanelPlaceholder.setManaged(false);
        eventListPanelPlaceholder.setVisible(true);
        eventListPanelPlaceholder.setManaged(true);
    }

}
