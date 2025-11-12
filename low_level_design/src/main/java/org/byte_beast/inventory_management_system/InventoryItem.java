package org.byte_beast.inventory_management_system;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

public class InventoryItem {

    private final String itemId;
    private final String productId;

    private final String wareHouseId;

    private final int quantity;

    private final int minStockLevel;
    private final int maxStockLevel;

    private final LocalDateTime createdAt;
    private final LocalDateTime updateAt;

    public InventoryItem(String itemId, String productId, String wareHouseId, int quantity, int minStockLevel, int maxStockLevel) {
        this.itemId = itemId;
        this.productId = productId;
        this.wareHouseId = wareHouseId;
        this.quantity = quantity;
        this.minStockLevel = minStockLevel;
        this.maxStockLevel = maxStockLevel;
        this.createdAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }

    public boolean isMinStock(){
        return quantity<minStockLevel;
    }

    public boolean isOverStock(){
        return quantity>minStockLevel;
    }

    public boolean isOutOf(){
        return quantity<1;
    }

    public InventoryItem updateQuantity(int newQuantity){
        return new InventoryItem(itemId,  productId,  wareHouseId, newQuantity,  minStockLevel,  maxStockLevel);
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
        return new StringJoiner(", ", InventoryItem.class.getSimpleName() + "[", "]")
                .add("itemId='" + itemId + "'")
                .add("productId='" + productId + "'")
                .add("wareHouseId='" + wareHouseId + "'")
                .add("quantity=" + quantity)
                .add("minStockLevel=" + minStockLevel)
                .add("maxStockLevel=" + maxStockLevel)
                .add("createdAt=" + createdAt)
                .add("updateAt=" + updateAt)
                .toString();
    }
}
