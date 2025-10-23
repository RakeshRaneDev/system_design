package org.byte_stroke.inventory_management_system;

import java.util.*;

public class InventoryRepository {
    // key is productId + "@" + locationId
    private final Map<String, InventoryItem> keyToInventoryItem = new HashMap<>();

    private String key(String productId, String locationId) {
        return productId + "@" + locationId;
    }

    public Optional<InventoryItem> find(String productId, String locationId) {
        return Optional.ofNullable(keyToInventoryItem.get(key(productId, locationId)));
    }

    public List<InventoryItem> findByProduct(String productId) {
        List<InventoryItem> result = new ArrayList<>();
        for (InventoryItem item : keyToInventoryItem.values()) {
            if (item.getProductId().equals(productId)) {
                result.add(item);
            }
        }
        return result;
    }

    public List<InventoryItem> findByLocation(String locationId) {
        List<InventoryItem> result = new ArrayList<>();
        for (InventoryItem item : keyToInventoryItem.values()) {
            if (item.getLocationId().equals(locationId)) {
                result.add(item);
            }
        }
        return result;
    }

    public void save(InventoryItem item) {
        keyToInventoryItem.put(key(item.getProductId(), item.getLocationId()), item);
    }

    public boolean delete(String productId, String locationId) {
        return keyToInventoryItem.remove(key(productId, locationId)) != null;
    }
}
