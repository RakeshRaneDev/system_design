package org.byte_stroke.inventory_management_system;

import java.time.LocalDateTime;

public class InventoryMovement {
    private final String productId;
    private final String fromLocationId; // nullable for adds
    private final String toLocationId;   // nullable for removals
    private final int quantityChange;    // positive increases, negative decreases
    private final MovementType movementType;
    private final LocalDateTime timestamp;
    private final String note;

    public InventoryMovement(
            String productId,
            String fromLocationId,
            String toLocationId,
            int quantityChange,
            MovementType movementType,
            LocalDateTime timestamp,
            String note
    ) {
        if (productId == null || productId.isBlank()) {
            throw new IllegalArgumentException("productId must be non-empty");
        }
        if (movementType == null) {
            throw new IllegalArgumentException("movementType must be provided");
        }
        this.productId = productId;
        this.fromLocationId = fromLocationId;
        this.toLocationId = toLocationId;
        this.quantityChange = quantityChange;
        this.movementType = movementType;
        this.timestamp = timestamp != null ? timestamp : LocalDateTime.now();
        this.note = note;
    }

    public String getProductId() { return productId; }
    public String getFromLocationId() { return fromLocationId; }
    public String getToLocationId() { return toLocationId; }
    public int getQuantityChange() { return quantityChange; }
    public MovementType getMovementType() { return movementType; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getNote() { return note; }
}
