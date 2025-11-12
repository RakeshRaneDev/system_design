package org.byte_stroke.inventory_management_system;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents an inventory item (product in a specific warehouse)
 */
public class InventoryItem {
    private final String itemId;
    private final String productId;
    private final String warehouseId;
    private int quantity;
    private final int minStockLevel;
    private final int maxStockLevel;
    private final LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;

    public InventoryItem(String itemId, String productId, String warehouseId,
                         int quantity, int minStockLevel, int maxStockLevel) {
        this.itemId = itemId;
        this.productId = productId;
        this.warehouseId = warehouseId;
        this.quantity = quantity;
        this.minStockLevel = minStockLevel;
        this.maxStockLevel = maxStockLevel;
        this.createdAt = LocalDateTime.now();
        this.lastUpdatedAt = LocalDateTime.now();
    }

    public InventoryItem(String itemId, String productId, String warehouseId,
                         int quantity, int minStockLevel, int maxStockLevel,
                         LocalDateTime createdAt, LocalDateTime lastUpdatedAt) {
        this.itemId = itemId;
        this.productId = productId;
        this.warehouseId = warehouseId;
        this.quantity = quantity;
        this.minStockLevel = minStockLevel;
        this.maxStockLevel = maxStockLevel;
        this.createdAt = createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
    }

    // Getters
    public String getItemId() { return itemId; }
    public String getProductId() { return productId; }
    public String getWarehouseId() { return warehouseId; }
    public int getQuantity() { return quantity; }
    public int getMinStockLevel() { return minStockLevel; }
    public int getMaxStockLevel() { return maxStockLevel; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getLastUpdatedAt() { return lastUpdatedAt; }

    // Stock level checks
    public boolean isLowStock() {
        return quantity <= minStockLevel;
    }

    public boolean isOverstocked() {
        return quantity >= maxStockLevel;
    }

    public boolean isOutOfStock() {
        return quantity <= 0;
    }

    // Update quantity
    public InventoryItem updateQuantity(int newQuantity) {
        return new InventoryItem(itemId, productId, warehouseId, newQuantity,
                                     minStockLevel, maxStockLevel, createdAt, LocalDateTime.now());
    }

    // Add quantity
    public InventoryItem addQuantity(int amount) {
        return updateQuantity(quantity + amount);
    }

    // Remove quantity
    public InventoryItem removeQuantity(int amount) {
        return updateQuantity(Math.max(0, quantity - amount));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryItem that = (InventoryItem) o;
        return Objects.equals(itemId, that.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId);
    }

    @Override
    public String toString() {
        return "SimpleInventoryItem{" +
                "itemId='" + itemId + '\'' +
                ", productId='" + productId + '\'' +
                ", warehouseId='" + warehouseId + '\'' +
                ", quantity=" + quantity +
                ", minStockLevel=" + minStockLevel +
                ", maxStockLevel=" + maxStockLevel +
                ", isLowStock=" + isLowStock() +
                '}';
    }
}
