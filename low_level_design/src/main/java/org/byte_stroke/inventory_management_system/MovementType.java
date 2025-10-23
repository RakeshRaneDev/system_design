package org.byte_stroke.inventory_management_system;

public enum MovementType {
    ADD,        // new stock added (initial add or new product)
    UPDATE,     // product or location info update (non-quantity)
    RESTOCK,    // increase quantity at a location
    REMOVE,     // remove product from catalog or location inventory
    TRANSFER,   // move quantity between locations
    ADJUST      // manual quantity adjustment (e.g., audit)
}
