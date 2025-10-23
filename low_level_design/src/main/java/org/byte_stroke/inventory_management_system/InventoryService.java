package org.byte_stroke.inventory_management_system;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class InventoryService {
    private final ProductRepository productRepository;
    private final LocationRepository locationRepository;
    private final InventoryRepository inventoryRepository;
    private final MovementRepository movementRepository;

    public InventoryService(
            ProductRepository productRepository,
            LocationRepository locationRepository,
            InventoryRepository inventoryRepository,
            MovementRepository movementRepository
    ) {
        this.productRepository = productRepository;
        this.locationRepository = locationRepository;
        this.inventoryRepository = inventoryRepository;
        this.movementRepository = movementRepository;
    }

    // Product operations
    public void addProduct(Product product) {
        productRepository.save(product);
        movementRepository.save(new InventoryMovement(
                product.getProductId(), null, null, 0, MovementType.ADD, LocalDateTime.now(), "Product added"
        ));
    }

    public void updateProduct(String productId, String name, String description) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));
        product.setName(name);
        product.setDescription(description);
        productRepository.save(product);
        movementRepository.save(new InventoryMovement(
                productId, null, null, 0, MovementType.UPDATE, LocalDateTime.now(), "Product updated"
        ));
    }

    public void removeProduct(String productId) {
        // remove all inventory items for this product across locations
        List<InventoryItem> items = inventoryRepository.findByProduct(productId);
        for (InventoryItem item : items) {
            inventoryRepository.delete(productId, item.getLocationId());
        }
        productRepository.delete(productId);
        movementRepository.save(new InventoryMovement(
                productId, null, null, 0, MovementType.REMOVE, LocalDateTime.now(), "Product removed"
        ));
    }

    // Location operations
    public void addLocation(Location location) {
        locationRepository.save(location);
    }

    public void updateLocation(String locationId, String name, String address) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new IllegalArgumentException("Location not found: " + locationId));
        location.setName(name);
        location.setAddress(address);
        locationRepository.save(location);
    }

    public void removeLocation(String locationId) {
        // remove all inventory at this location
        for (InventoryItem item : inventoryRepository.findByLocation(locationId)) {
            inventoryRepository.delete(item.getProductId(), locationId);
        }
        locationRepository.delete(locationId);
    }

    // Inventory operations
    public void restock(String productId, String locationId, int quantity, String note) {
        ensureProductAndLocation(productId, locationId);
        if (quantity <= 0) throw new IllegalArgumentException("quantity must be positive");
        Optional<InventoryItem> existing = inventoryRepository.find(productId, locationId);
        InventoryItem item = existing.orElseGet(() -> new InventoryItem(productId, locationId, 0));
        item.increase(quantity);
        inventoryRepository.save(item);
        movementRepository.save(new InventoryMovement(
                productId, null, locationId, quantity, MovementType.RESTOCK, LocalDateTime.now(), note
        ));
    }

    public void adjust(String productId, String locationId, int delta, String note) {
        ensureProductAndLocation(productId, locationId);
        Optional<InventoryItem> existing = inventoryRepository.find(productId, locationId);
        InventoryItem item = existing.orElseGet(() -> new InventoryItem(productId, locationId, 0));
        if (delta >= 0) item.increase(delta); else item.decrease(-delta);
        inventoryRepository.save(item);
        movementRepository.save(new InventoryMovement(
                productId, null, locationId, delta, MovementType.ADJUST, LocalDateTime.now(), note
        ));
    }

    public void transfer(String productId, String fromLocationId, String toLocationId, int quantity, String note) {
        ensureProductAndLocation(productId, fromLocationId);
        ensureProductAndLocation(productId, toLocationId);
        if (fromLocationId.equals(toLocationId)) {
            throw new IllegalArgumentException("from and to locations must differ");
        }
        if (quantity <= 0) throw new IllegalArgumentException("quantity must be positive");

        InventoryItem fromItem = inventoryRepository.find(productId, fromLocationId)
                .orElseThrow(() -> new IllegalArgumentException("No stock at source location"));
        fromItem.decrease(quantity);
        inventoryRepository.save(fromItem);

        InventoryItem toItem = inventoryRepository.find(productId, toLocationId)
                .orElseGet(() -> new InventoryItem(productId, toLocationId, 0));
        toItem.increase(quantity);
        inventoryRepository.save(toItem);

        movementRepository.save(new InventoryMovement(
                productId, fromLocationId, toLocationId, quantity, MovementType.TRANSFER, LocalDateTime.now(), note
        ));
    }

    public int getStock(String productId, String locationId) {
        return inventoryRepository.find(productId, locationId).map(InventoryItem::getQuantity).orElse(0);
    }

    public int getTotalStock(String productId) {
        int total = 0;
        for (InventoryItem item : inventoryRepository.findByProduct(productId)) {
            total += item.getQuantity();
        }
        return total;
    }

    public List<InventoryMovement> getMovementsForProduct(String productId) {
        return movementRepository.findByProduct(productId);
    }

    private void ensureProductAndLocation(String productId, String locationId) {
        productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));
        locationRepository.findById(locationId)
                .orElseThrow(() -> new IllegalArgumentException("Location not found: " + locationId));
    }
}
