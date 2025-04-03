package trackup.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.beans.property.BooleanProperty;

public class VisibilityTest {

    private Visibility visibility;

    @BeforeEach
    public void setUp() {
        visibility = new Visibility();
    }

    @Test
    public void defaultConstructor_allFieldsTrue() {
        assertTrue(visibility.isShowId());
        assertTrue(visibility.isShowName());
        assertTrue(visibility.isShowPhone());
        assertTrue(visibility.isShowEmail());
        assertTrue(visibility.isShowAddress());
        assertTrue(visibility.isShowTag());
        assertTrue(visibility.isShowCategory());
        assertTrue(visibility.isShowNote());
        assertTrue(visibility.isShowDatetime());
    }

    @Test
    public void setShowId_updatesProperty_onlySetterChangesState() {
        BooleanProperty prop = visibility.showIdProperty();
        assertTrue(prop.get());
        visibility.setShowId(false);
        assertFalse(visibility.isShowId());
        assertFalse(prop.get());

        prop.set(true);
        assertFalse(visibility.isShowId());
        assertTrue(prop.get());
        assertEquals(prop, visibility.showIdProperty());
    }

    @Test
    public void setShowName_updatesProperty_onlySetterChangesState() {
        BooleanProperty prop = visibility.showNameProperty();
        assertTrue(prop.get());
        visibility.setShowName(false);
        assertFalse(visibility.isShowName());
        assertFalse(prop.get());

        prop.set(true);
        assertFalse(visibility.isShowName());
        assertTrue(prop.get());
        assertEquals(prop, visibility.showNameProperty());
    }

    @Test
    public void setShowPhone_updatesProperty_onlySetterChangesState() {
        BooleanProperty prop = visibility.showPhoneProperty();
        assertTrue(prop.get());
        visibility.setShowPhone(false);
        assertFalse(visibility.isShowPhone());
        assertFalse(prop.get());

        prop.set(true);
        assertFalse(visibility.isShowPhone());
        assertTrue(prop.get());
        assertEquals(prop, visibility.showPhoneProperty());
    }

    @Test
    public void setShowEmail_updatesProperty_onlySetterChangesState() {
        BooleanProperty prop = visibility.showEmailProperty();
        assertTrue(prop.get());
        visibility.setShowEmail(false);
        assertFalse(visibility.isShowEmail());
        assertFalse(prop.get());

        prop.set(true);
        assertFalse(visibility.isShowEmail());
        assertTrue(prop.get());
        assertEquals(prop, visibility.showEmailProperty());
    }

    @Test
    public void setShowAddress_updatesProperty_onlySetterChangesState() {
        BooleanProperty prop = visibility.showAddressProperty();
        assertTrue(prop.get());
        visibility.setShowAddress(false);
        assertFalse(visibility.isShowAddress());
        assertFalse(prop.get());

        prop.set(true);
        assertFalse(visibility.isShowAddress());
        assertTrue(prop.get());
        assertEquals(prop, visibility.showAddressProperty());
    }

    @Test
    public void setShowTag_updatesProperty_onlySetterChangesState() {
        BooleanProperty prop = visibility.showTagProperty();
        assertTrue(prop.get());
        visibility.setShowTag(false);
        assertFalse(visibility.isShowTag());
        assertFalse(prop.get());

        prop.set(true);
        assertFalse(visibility.isShowTag());
        assertTrue(prop.get());
        assertEquals(prop, visibility.showTagProperty());
    }

    @Test
    public void setShowCategory_updatesProperty_onlySetterChangesState() {
        BooleanProperty prop = visibility.showCategoryProperty();
        assertTrue(prop.get());
        visibility.setShowCategory(false);
        assertFalse(visibility.isShowCategory());
        assertFalse(prop.get());

        prop.set(true);
        assertFalse(visibility.isShowCategory());
        assertTrue(prop.get());
        assertEquals(prop, visibility.showCategoryProperty());
    }

    @Test
    public void setShowNote_updatesProperty_onlySetterChangesState() {
        BooleanProperty prop = visibility.showNoteProperty();
        assertTrue(prop.get());
        visibility.setShowNote(false);
        assertFalse(visibility.isShowNote());
        assertFalse(prop.get());

        prop.set(true);
        assertFalse(visibility.isShowNote());
        assertTrue(prop.get());
        assertEquals(prop, visibility.showNoteProperty());
    }

    @Test
    public void setShowDatetime_updatesProperty_onlySetterChangesState() {
        BooleanProperty prop = visibility.showDatetimeProperty();
        assertTrue(prop.get());
        visibility.setShowDatetime(false);
        assertFalse(visibility.isShowDatetime());
        assertFalse(prop.get());

        prop.set(true);
        assertFalse(visibility.isShowDatetime());
        assertTrue(prop.get());
        assertEquals(prop, visibility.showDatetimeProperty());
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        Visibility other = new Visibility();
        assertEquals(visibility, other);

        visibility.setShowNote(false);
        other.setShowNote(false);
        visibility.setShowDatetime(false);
        other.setShowDatetime(false);
        assertEquals(visibility, other);
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        Visibility other = new Visibility();
        other.setShowPhone(false);
        assertNotEquals(visibility, other);

        other = new Visibility();
        other.setShowNote(false);
        assertNotEquals(visibility, other);

        other = new Visibility();
        other.setShowDatetime(false);
        assertNotEquals(visibility, other);
    }

    @Test
    public void hashCode_consistentWithEquals() {
        Visibility a = new Visibility();
        Visibility b = new Visibility();
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());

        b.setShowCategory(false);
        assertNotEquals(a.hashCode(), b.hashCode());

        b = new Visibility();
        b.setShowDatetime(false);
        assertNotEquals(a.hashCode(), b.hashCode());

        b = new Visibility();
        b.setShowNote(false);
        assertNotEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void toString_containsAllFieldStates() {
        String result = visibility.toString();
        assertTrue(result.contains("showId=true"));
        assertTrue(result.contains("showName=true"));
        assertTrue(result.contains("showPhone=true"));
        assertTrue(result.contains("showEmail=true"));
        assertTrue(result.contains("showAddress=true"));
        assertTrue(result.contains("showTag=true"));
        assertTrue(result.contains("showCategory=true"));
        assertTrue(result.contains("showNote=true"));
        assertTrue(result.contains("showDatetime=true"));
    }
}
