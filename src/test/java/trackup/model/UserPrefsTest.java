package trackup.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static trackup.testutil.Assert.assertThrows;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import trackup.commons.core.GuiSettings;
import trackup.commons.core.Visibility;

public class UserPrefsTest {

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        UserPrefs userPref = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPref.setGuiSettings(null));
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setAddressBookFilePath(null));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        UserPrefs userPrefs = new UserPrefs();
        assertEquals(userPrefs, userPrefs);
    }

    @Test
    public void equals_differentType_returnsFalse() {
        UserPrefs userPrefs = new UserPrefs();
        assertNotEquals(userPrefs, 1); // Comparing with different type
    }

    @Test
    public void equals_differentGuiSettings_returnsFalse() {
        UserPrefs userPrefs1 = new UserPrefs();
        UserPrefs userPrefs2 = new UserPrefs();
        userPrefs2.setGuiSettings(new GuiSettings(200, 200, 5, 5, new Visibility()));
        assertNotEquals(userPrefs1, userPrefs2);
    }

    @Test
    public void equals_differentFilePath_returnsFalse() {
        UserPrefs userPrefs1 = new UserPrefs();
        UserPrefs userPrefs2 = new UserPrefs();
        userPrefs2.setAddressBookFilePath(Paths.get("different", "path.json"));
        assertNotEquals(userPrefs1, userPrefs2);
    }

    @Test
    public void hashCode_sameValues_returnsSameHashCode() {
        UserPrefs userPrefs1 = new UserPrefs();
        UserPrefs userPrefs2 = new UserPrefs();
        assertEquals(userPrefs1.hashCode(), userPrefs2.hashCode());
    }

    @Test
    public void hashCode_differentGuiSettings_returnsDifferentHashCode() {
        UserPrefs userPrefs1 = new UserPrefs();
        UserPrefs userPrefs2 = new UserPrefs();
        userPrefs2.setGuiSettings(new GuiSettings(300, 300, 10, 10, new Visibility()));
        assertNotEquals(userPrefs1.hashCode(), userPrefs2.hashCode());
    }
}
