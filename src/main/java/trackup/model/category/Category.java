package trackup.model.category;

import static java.util.Objects.requireNonNull;
import static trackup.commons.util.AppUtil.checkArgument;

/**
 * Represents a Category in the address book.
 * Guarantees: immutable; value is one of the predefined valid categories.
 */
public class Category {

    public static final String MESSAGE_CONSTRAINTS = "Category should be one of: Client, Investor, Partner, Other";
    private static final String[] VALID_CATEGORIES = {"Client", "Investor", "Partner", "Other"};

    public static final String DEFAULT_CATEGORY = "Other";
    public final String categoryName;

    /**
     * Constructs a {@code Category}.
     *
     * @param categoryName A valid category name.
     */
    public Category(String categoryName) {
        requireNonNull(categoryName);
        checkArgument(isValidCategoryName(categoryName), MESSAGE_CONSTRAINTS);
        this.categoryName = categoryName;
    }

    /**
     * Returns true if a given string is a valid category name.
     */
    public static boolean isValidCategoryName(String test) {
        for (String validCategory : VALID_CATEGORIES) {
            if (validCategory.equals(test)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Category)) {
            return false;
        }

        Category otherCategory = (Category) other;
        return categoryName.equals(otherCategory.categoryName);
    }

    @Override
    public int hashCode() {
        return categoryName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + categoryName + ']';
    }
}
