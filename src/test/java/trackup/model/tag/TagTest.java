package trackup.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static trackup.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

    @Test
    public void equals() {
        Tag newTag1 = new Tag("abc");
        Tag newTag2 = new Tag("abc");
        Tag newTag3 = new Tag("def");

        // same object -> returns true
        assertEquals(newTag1, newTag1);

        // same values -> returns true
        assertEquals(newTag1, newTag2);

        // different type -> returns false
        assertNotEquals(newTag1, 1);

        // different values -> returns false
        assertNotEquals(newTag1, newTag3);
    }

}
