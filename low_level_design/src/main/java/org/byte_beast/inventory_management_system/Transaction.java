package org.byte_beast.inventory_management_system;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

public class Transaction {
    private final String transactionId;
    private final String productId;
    private final String itemId;

    private final String wareHouseId;
    private final  TransactionType type;

    private final int quantity;
    private final String reason;
    private final String note;
    private final LocalDateTime createdAt;

    public Transaction(String transactionId, String productId, String itemId, String wareHouseId, TransactionType type, int quantity, String reason, String note ) {
        this.transactionId = transactionId;
        this.productId = productId;
        this.itemId = itemId;
        this.wareHouseId = wareHouseId;
        this.type = type;
        this.quantity = quantity;
        this.reason = reason;
        this.note = note;
        this.createdAt = LocalDateTime.now();
    }


    public String getTransactionId() {
        return transactionId;
    }

    public String getProductId() {
        return productId;
    }

    public String getItemId() {
        return itemId;
    }

    public String getWareHouseId() {
        return wareHouseId;
    }

    public TransactionType getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getReason() {
        return reason;
    }

    public String getNote() {
        return note;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

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
        return new StringJoiner(", ", Transaction.class.getSimpleName() + "[", "]")
                .add("transactionId='" + transactionId + "'")
                .add("productId='" + productId + "'")
                .add("itemId='" + itemId + "'")
                .add("wareHouseId='" + wareHouseId + "'")
                .add("type=" + type)
                .add("quantity=" + quantity)
                .add("reason='" + reason + "'")
                .add("note='" + note + "'")
                .add("createdAt=" + createdAt)
                .toString();
    }
}
