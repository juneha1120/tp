package trackup.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import trackup.commons.core.LogsCenter;
import trackup.commons.core.Visibility;
import trackup.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    private final Visibility visibility;

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList, Visibility visibility) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        this.visibility = visibility;

        visibility.showIdProperty().addListener((obs, oldVal, newVal) -> personListView.refresh());
        visibility.showNameProperty().addListener((obs, oldVal, newVal) -> personListView.refresh());
        visibility.showPhoneProperty().addListener((obs, oldVal, newVal) -> personListView.refresh());
        visibility.showEmailProperty().addListener((obs, oldVal, newVal) -> personListView.refresh());
        visibility.showAddressProperty().addListener((obs, oldVal, newVal) -> personListView.refresh());
        visibility.showTagProperty().addListener((obs, oldVal, newVal) -> personListView.refresh());
        visibility.showCategoryProperty().addListener((obs, oldVal, newVal) -> personListView.refresh());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1, visibility).getRoot());
            }
        }
    }

}
