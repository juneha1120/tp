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

    @Test
    public void hashCode_test() {
        Category category = new Category("Client");

        // same values -> returns same hash code
        assertTrue(category.hashCode() == new Category("Client").hashCode());

        // different values -> returns different hash code
        assertFalse(category.hashCode() == new Category("Investor").hashCode());
    }

    @Test
    public void toString_test() {
        Category category = new Category("Partner");

        // correct format -> returns true
        assertTrue(category.toString().equals("[Partner]"));

        // incorrect format -> returns false
        assertFalse(category.toString().equals("Partner"));
        assertFalse(category.toString().equals("[Client]"));
    }
}
