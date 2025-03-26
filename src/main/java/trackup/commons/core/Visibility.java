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

    public Visibility() {}

    public boolean isShowId() {
        return showId;
    }

    public void setShowId(boolean value) {
        this.showId = value;
        if (showIdProperty != null) {
            showIdProperty.set(value);
        }
    }

    public BooleanProperty showIdProperty() {
        if (showIdProperty == null) {
            showIdProperty = new SimpleBooleanProperty(showId);
        }
        return showIdProperty;
    }

    public boolean isShowName() {
        return showName;
    }

    public void setShowName(boolean value) {
        this.showName = value;
        if (showNameProperty != null) {
            showNameProperty.set(value);
        }
    }

    public BooleanProperty showNameProperty() {
        if (showNameProperty == null) {
            showNameProperty = new SimpleBooleanProperty(showName);
        }
        return showNameProperty;
    }

    public boolean isShowPhone() {
        return showPhone;
    }

    public void setShowPhone(boolean value) {
        this.showPhone = value;
        if (showPhoneProperty != null) {
            showPhoneProperty.set(value);
        }
    }

    public BooleanProperty showPhoneProperty() {
        if (showPhoneProperty == null) {
            showPhoneProperty = new SimpleBooleanProperty(showPhone);
        }
        return showPhoneProperty;
    }

    public boolean isShowEmail() {
        return showEmail;
    }

    public void setShowEmail(boolean value) {
        this.showEmail = value;
        if (showEmailProperty != null) {
            showEmailProperty.set(value);
        }
    }

    public BooleanProperty showEmailProperty() {
        if (showEmailProperty == null) {
            showEmailProperty = new SimpleBooleanProperty(showEmail);
        }
        return showEmailProperty;
    }

    public boolean isShowAddress() {
        return showAddress;
    }

    public void setShowAddress(boolean value) {
        this.showAddress = value;
        if (showAddressProperty != null) {
            showAddressProperty.set(value);
        }
    }

    public BooleanProperty showAddressProperty() {
        if (showAddressProperty == null) {
            showAddressProperty = new SimpleBooleanProperty(showAddress);
        }
        return showAddressProperty;
    }

    public boolean isShowTag() {
        return showTag;
    }

    public void setShowTag(boolean value) {
        this.showTag = value;
        if (showTagProperty != null) {
            showTagProperty.set(value);
        }
    }

    public BooleanProperty showTagProperty() {
        if (showTagProperty == null) {
            showTagProperty = new SimpleBooleanProperty(showTag);
        }
        return showTagProperty;
    }

    public boolean isShowCategory() {
        return showCategory;
    }

    public void setShowCategory(boolean value) {
        this.showCategory = value;
        if (showCategoryProperty != null) {
            showCategoryProperty.set(value);
        }
    }

    public BooleanProperty showCategoryProperty() {
        if (showCategoryProperty == null) {
            showCategoryProperty = new SimpleBooleanProperty(showCategory);
        }
        return showCategoryProperty;
    }

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
