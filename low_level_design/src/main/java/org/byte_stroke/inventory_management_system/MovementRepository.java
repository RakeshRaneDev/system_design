package org.byte_stroke.inventory_management_system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MovementRepository {
    private final List<InventoryMovement> movements = new ArrayList<>();

    public void save(InventoryMovement movement) {
        movements.add(movement);
    }

    public List<InventoryMovement> findAll() {
        return Collections.unmodifiableList(movements);
    }

    public List<InventoryMovement> findByProduct(String productId) {
        List<InventoryMovement> result = new ArrayList<>();
        for (InventoryMovement m : movements) {
            if (m.getProductId().equals(productId)) {
                result.add(m);
            }
        }
        return result;
    }
}
