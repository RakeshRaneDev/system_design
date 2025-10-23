package org.byte_stroke.inventory_management_system;

public class InventoryApp {
    public static void main(String[] args) {
        ProductRepository productRepo = new ProductRepository();
        LocationRepository locationRepo = new LocationRepository();
        InventoryRepository inventoryRepo = new InventoryRepository();
        MovementRepository movementRepo = new MovementRepository();
        InventoryService service = new InventoryService(productRepo, locationRepo, inventoryRepo, movementRepo);

        // Setup
        Product p1 = new Product("P-100", "Laptop", "14 inch business laptop");
        Product p2 = new Product("P-200", "Mouse", "Wireless mouse");
        service.addProduct(p1);
        service.addProduct(p2);

        Location l1 = new Location("LOC-DEL", "Delhi Warehouse", "Delhi");
        Location l2 = new Location("LOC-BLR", "Bangalore Warehouse", "Bangalore");
        service.addLocation(l1);
        service.addLocation(l2);

        // Restock
        service.restock("P-100", "LOC-DEL", 10, "Initial stock");
        service.restock("P-100", "LOC-BLR", 5, "Initial stock");
        service.restock("P-200", "LOC-DEL", 20, "Initial stock");

        // Transfer
        service.transfer("P-200", "LOC-DEL", "LOC-BLR", 5, "Balancing stock");

        // Adjust
        service.adjust("P-100", "LOC-DEL", -2, "Damaged units");

        // Query
        System.out.println("Stock P-100 at DEL: " + service.getStock("P-100", "LOC-DEL"));
        System.out.println("Stock P-100 at BLR: " + service.getStock("P-100", "LOC-BLR"));
        System.out.println("Total P-100: " + service.getTotalStock("P-100"));

        System.out.println("\nMovements for P-100:");
        for (InventoryMovement m : service.getMovementsForProduct("P-100")) {
            System.out.println(m.getTimestamp() + " | " + m.getMovementType() + " | from=" + m.getFromLocationId() + " to=" + m.getToLocationId() + " qty=" + m.getQuantityChange());
        }
    }
}
