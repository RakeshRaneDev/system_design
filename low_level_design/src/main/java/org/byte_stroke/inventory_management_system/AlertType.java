package org.byte_stroke.inventory_management_system;

public enum AlertType {
    LOW_STOCK("Low Stock"),
    OUT_OF_STOCK("Out of Stock"),
    OVERSTOCK("Overstock"),
    EXPIRY_WARNING("Expiry Warning"),
    EXPIRED("Expired");

    private final String description;

    AlertType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
