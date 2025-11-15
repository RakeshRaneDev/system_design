package org.byte_stroke.inventory_management_system;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a transaction in the inventory system
 */
public class Transaction {
    private final String transactionId;
    private final String itemId;
    private final String productId;
    private final String warehouseId;
    private final TransactionType type;
    private final int quantity;
    private final String reason;
    private final String userId;
    private final LocalDateTime timestamp;
    private final String notes;

    public Transaction(String transactionId, String itemId, String productId, String warehouseId,
                       TransactionType type, int quantity, String reason, String userId, String notes) {
        this.transactionId = transactionId;
        this.itemId = itemId;
        this.productId = productId;
        this.warehouseId = warehouseId;
        this.type = type;
        this.quantity = quantity;
        this.reason = reason;
        this.userId = userId;
        this.timestamp = LocalDateTime.now();
        this.notes = notes;
    }

    public Transaction(String transactionId, String itemId, String productId, String warehouseId,
                       TransactionType type, int quantity, String reason, String userId,
                       String notes, LocalDateTime timestamp) {
        this.transactionId = transactionId;
        this.itemId = itemId;
        this.productId = productId;
        this.warehouseId = warehouseId;
        this.type = type;
        this.quantity = quantity;
        this.reason = reason;
        this.userId = userId;
        this.timestamp = timestamp;
        this.notes = notes;
    }

    // Getters
    public String getTransactionId() { return transactionId; }
    public String getItemId() { return itemId; }
    public String getProductId() { return productId; }
    public String getWarehouseId() { return warehouseId; }
    public TransactionType getType() { return type; }
    public int getQuantity() { return quantity; }
    public String getReason() { return reason; }
    public String getUserId() { return userId; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getNotes() { return notes; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(transactionId, that.transactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId);
    }

    @Override
    public String toString() {
        return "SimpleTransaction{" +
                "transactionId='" + transactionId + '\'' +
                ", itemId='" + itemId + '\'' +
                ", type=" + type +
                ", quantity=" + quantity +
                ", reason='" + reason + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

}
