package seedu.address.model.category;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Category(null));
    }

    @Test
    public void constructor_invalidCategory_throwsIllegalArgumentException() {
        String invalidCategory = "InvalidCategory";
        assertThrows(IllegalArgumentException.class, () -> new Category(invalidCategory));
    }

    @Test
    public void isValidCategoryName() {
        // invalid category names
        assertFalse(Category.isValidCategoryName("")); // empty string
        assertFalse(Category.isValidCategoryName("Random")); // not in predefined categories

        // valid category names
        assertTrue(Category.isValidCategoryName("Client"));
        assertTrue(Category.isValidCategoryName("Investor"));
        assertTrue(Category.isValidCategoryName("Partner"));
        assertTrue(Category.isValidCategoryName("Other"));
    }

    @Test
    public void equals() {
        Category category = new Category("Client");

        // same values -> returns true
        assertTrue(category.equals(new Category("Client")));

        // same object -> returns true
        assertTrue(category.equals(category));

        // null -> returns false
        assertFalse(category.equals(null));

        // different types -> returns false
        assertFalse(category.equals(5.0f));

        // different values -> returns false
        assertFalse(category.equals(new Category("Investor")));
    }
}
