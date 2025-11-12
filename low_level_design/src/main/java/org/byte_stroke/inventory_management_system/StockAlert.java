package org.byte_stroke.inventory_management_system;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a stock alert in the inventory system
 */
public class StockAlert {
    private final String alertId;
    private final String itemId;
    private final String productId;
    private final String warehouseId;
    private final AlertType type;
    private final String message;
    private final int currentQuantity;
    private final int threshold;
    private final LocalDateTime createdAt;
    private boolean isResolved;
    private final LocalDateTime resolvedAt;

    public StockAlert(String alertId, String itemId, String productId, String warehouseId,
                      AlertType type, String message, int currentQuantity, int threshold) {
        this.alertId = alertId;
        this.itemId = itemId;
        this.productId = productId;
        this.warehouseId = warehouseId;
        this.type = type;
        this.message = message;
        this.currentQuantity = currentQuantity;
        this.threshold = threshold;
        this.createdAt = LocalDateTime.now();
        this.isResolved = false;
        this.resolvedAt = null;
    }

    public StockAlert(String alertId, String itemId, String productId, String warehouseId,
                      AlertType type, String message, int currentQuantity, int threshold,
                      LocalDateTime createdAt, boolean isResolved, LocalDateTime resolvedAt) {
        this.alertId = alertId;
        this.itemId = itemId;
        this.productId = productId;
        this.warehouseId = warehouseId;
        this.type = type;
        this.message = message;
        this.currentQuantity = currentQuantity;
        this.threshold = threshold;
        this.createdAt = createdAt;
        this.isResolved = isResolved;
        this.resolvedAt = resolvedAt;
    }

    // Getters
    public String getAlertId() { return alertId; }
    public String getItemId() { return itemId; }
    public String getProductId() { return productId; }
    public String getWarehouseId() { return warehouseId; }
    public AlertType getType() { return type; }
    public String getMessage() { return message; }
    public int getCurrentQuantity() { return currentQuantity; }
    public int getThreshold() { return threshold; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public boolean isResolved() { return isResolved; }
    public LocalDateTime getResolvedAt() { return resolvedAt; }

    // Create resolved alert
    public StockAlert resolve() {
        return new StockAlert(alertId, itemId, productId, warehouseId, type, message,
                                  currentQuantity, threshold, createdAt, true, LocalDateTime.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockAlert that = (StockAlert) o;
        return Objects.equals(alertId, that.alertId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alertId);
    }

    @Override
    public String toString() {
        return "SimpleStockAlert{" +
                "alertId='" + alertId + '\'' +
                ", itemId='" + itemId + '\'' +
                ", type=" + type +
                ", message='" + message + '\'' +
                ", currentQuantity=" + currentQuantity +
                ", isResolved=" + isResolved +
                '}';
    }
}
