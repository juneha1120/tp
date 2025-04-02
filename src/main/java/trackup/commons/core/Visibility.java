package trackup.commons.core;

import java.util.Objects;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import trackup.commons.util.ToStringBuilder;

/**
 * A class to control the visibility of specific UI elements in the PersonCard.
 * <p>
 * This class supports both JSON serialization (via plain boolean fields)
 * and JavaFX property bindings (via lazily initialized transient properties).
 */
public class Visibility {
    private boolean showId = true;
    private boolean showName = true;
    private boolean showPhone = true;
    private boolean showEmail = true;
    private boolean showAddress = true;
    private boolean showTag = true;
    private boolean showCategory = true;

    // Transient JavaFX properties (not serialized)
    private transient BooleanProperty showIdProperty;
    private transient BooleanProperty showNameProperty;
    private transient BooleanProperty showPhoneProperty;
    private transient BooleanProperty showEmailProperty;
    private transient BooleanProperty showAddressProperty;
    private transient BooleanProperty showTagProperty;
    private transient BooleanProperty showCategoryProperty;

    /**
     * Constructs a {@code Visibility} object with all UI elements set to be visible by default.
     */
    public Visibility() {}

    /**
     * Returns whether the ID should be visible.
     */
    public boolean isShowId() {
        return showId;
    }

    /**
     * Sets whether the ID should be visible.
     */
    public void setShowId(boolean value) {
        this.showId = value;
        if (showIdProperty != null) {
            showIdProperty.set(value);
        }
    }

    /**
     * Returns the {@link BooleanProperty} for ID visibility, allowing UI binding.
     */
    public BooleanProperty showIdProperty() {
        if (showIdProperty == null) {
            showIdProperty = new SimpleBooleanProperty(showId);
        }
        return showIdProperty;
    }

    /**
     * Returns whether the name should be visible.
     */
    public boolean isShowName() {
        return showName;
    }

    /**
     * Sets whether the name should be visible.
     */
    public void setShowName(boolean value) {
        this.showName = value;
        if (showNameProperty != null) {
            showNameProperty.set(value);
        }
    }

    /**
     * Returns the {@link BooleanProperty} for name visibility, allowing UI binding.
     */
    public BooleanProperty showNameProperty() {
        if (showNameProperty == null) {
            showNameProperty = new SimpleBooleanProperty(showName);
        }
        return showNameProperty;
    }

    /**
     * Returns whether the phone should be visible.
     */
    public boolean isShowPhone() {
        return showPhone;
    }

    /**
     * Sets whether the phone should be visible.
     */
    public void setShowPhone(boolean value) {
        this.showPhone = value;
        if (showPhoneProperty != null) {
            showPhoneProperty.set(value);
        }
    }

    /**
     * Returns the {@link BooleanProperty} for phone visibility, allowing UI binding.
     */
    public BooleanProperty showPhoneProperty() {
        if (showPhoneProperty == null) {
            showPhoneProperty = new SimpleBooleanProperty(showPhone);
        }
        return showPhoneProperty;
    }

    /**
     * Returns whether the email should be visible.
     */
    public boolean isShowEmail() {
        return showEmail;
    }

    /**
     * Sets whether the email should be visible.
     */
    public void setShowEmail(boolean value) {
        this.showEmail = value;
        if (showEmailProperty != null) {
            showEmailProperty.set(value);
        }
    }

    /**
     * Returns the {@link BooleanProperty} for email visibility, allowing UI binding.
     */
    public BooleanProperty showEmailProperty() {
        if (showEmailProperty == null) {
            showEmailProperty = new SimpleBooleanProperty(showEmail);
        }
        return showEmailProperty;
    }

    /**
     * Returns whether the address should be visible.
     */
    public boolean isShowAddress() {
        return showAddress;
    }

    /**
     * Sets whether the address should be visible.
     */
    public void setShowAddress(boolean value) {
        this.showAddress = value;
        if (showAddressProperty != null) {
            showAddressProperty.set(value);
        }
    }

    /**
     * Returns the {@link BooleanProperty} for address visibility, allowing UI binding.
     */
    public BooleanProperty showAddressProperty() {
        if (showAddressProperty == null) {
            showAddressProperty = new SimpleBooleanProperty(showAddress);
        }
        return showAddressProperty;
    }

    /**
     * Returns whether the tags should be visible.
     */
    public boolean isShowTag() {
        return showTag;
    }

    /**
     * Sets whether the tags should be visible.
     */
    public void setShowTag(boolean value) {
        this.showTag = value;
        if (showTagProperty != null) {
            showTagProperty.set(value);
        }
    }

    /**
     * Returns the {@link BooleanProperty} for tag visibility, allowing UI binding.
     */
    public BooleanProperty showTagProperty() {
        if (showTagProperty == null) {
            showTagProperty = new SimpleBooleanProperty(showTag);
        }
        return showTagProperty;
    }

    /**
     * Returns whether the category should be visible.
     */
    public boolean isShowCategory() {
        return showCategory;
    }

    /**
     * Sets whether the category should be visible.
     */
    public void setShowCategory(boolean value) {
        this.showCategory = value;
        if (showCategoryProperty != null) {
            showCategoryProperty.set(value);
        }
    }

    /**
     * Returns the {@link BooleanProperty} for category visibility, allowing UI binding.
     */
    public BooleanProperty showCategoryProperty() {
        if (showCategoryProperty == null) {
            showCategoryProperty = new SimpleBooleanProperty(showCategory);
        }
        return showCategoryProperty;
    }

    /**
     * Returns true if this {@code Visibility} is equal to another.
     * Compares all boolean visibility values.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Visibility otherVisibility)) {
            return false;
        }

        return isShowId() == otherVisibility.isShowId()
                && isShowName() == otherVisibility.isShowName()
                && isShowPhone() == otherVisibility.isShowPhone()
                && isShowEmail() == otherVisibility.isShowEmail()
                && isShowAddress() == otherVisibility.isShowAddress()
                && isShowTag() == otherVisibility.isShowTag()
                && isShowCategory() == otherVisibility.isShowCategory();
    }

    /**
     * Returns the hash code based on all boolean visibility values.
     */
    @Override
    public int hashCode() {
        return Objects.hash(
                isShowId(),
                isShowName(),
                isShowPhone(),
                isShowEmail(),
                isShowAddress(),
                isShowTag(),
                isShowCategory()
        );
    }

    /**
     * Returns a string representation of this {@code Visibility} for debugging.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("showId", isShowId())
                .add("showName", isShowName())
                .add("showPhone", isShowPhone())
                .add("showEmail", isShowEmail())
                .add("showAddress", isShowAddress())
                .add("showTag", isShowTag())
                .add("showCategory", isShowCategory())
                .toString();
    }
}
