package org.byte_stroke.inventory_management_system;

import java.util.Objects;

public class InventoryItem {
    private final String productId;
    private final String locationId;
    private int quantity;

    public InventoryItem(String productId, String locationId, int quantity) {
        if (productId == null || productId.isBlank()) {
            throw new IllegalArgumentException("productId must be non-empty");
        }
        if (locationId == null || locationId.isBlank()) {
            throw new IllegalArgumentException("locationId must be non-empty");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("quantity cannot be negative");
        }
        this.productId = productId;
        this.locationId = locationId;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public String getLocationId() {
        return locationId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) throw new IllegalArgumentException("quantity cannot be negative");
        this.quantity = quantity;
    }

    public void increase(int delta) {
        if (delta < 0) throw new IllegalArgumentException("delta cannot be negative");
        this.quantity += delta;
    }

    public void decrease(int delta) {
        if (delta < 0) throw new IllegalArgumentException("delta cannot be negative");
        if (this.quantity - delta < 0) throw new IllegalStateException("insufficient stock to decrease");
        this.quantity -= delta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryItem that = (InventoryItem) o;
        return productId.equals(that.productId) && locationId.equals(that.locationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, locationId);
    }

    @Override
    public String toString() {
        return "InventoryItem{" +
                "productId='" + productId + '\'' +
                ", locationId='" + locationId + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
