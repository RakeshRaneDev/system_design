package org.byte_stroke.inventory_management_system;

import java.util.*;

/**
 * Demo application showcasing the Simple Inventory Management System
 */
public class InventoryManagementSystemApplication {
    public static void main(String[] args) {
        System.out.println("=== Simple Inventory Management System Demo ===\n");
        
        // Create inventory system
        InventoryManagementSystem inventory = new InventoryManagementSystem("demo-inventory");
        
        try {
            // Demo 1: Product Management
            demonstrateProductManagement(inventory);
            
            // Demo 2: Warehouse Management
            demonstrateWarehouseManagement(inventory);
            
            // Demo 3: Inventory Management
            demonstrateInventoryManagement(inventory);
            
            // Demo 4: Stock Operations
            demonstrateStockOperations(inventory);
            
            // Demo 5: Alerts and Reporting
            demonstrateAlertsAndReporting(inventory);
            
        } catch (Exception e) {
            System.err.println("Error during demo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void demonstrateProductManagement(InventoryManagementSystem inventory) {
        System.out.println("=== 1. Product Management ===");
        
        // Add products
        System.out.println("Adding products:");
        inventory.addProduct("PROD-001", "Laptop", "High-performance laptop", "Electronics", 999.99, "unit");
        inventory.addProduct("PROD-002", "Mouse", "Wireless mouse", "Electronics", 29.99, "unit");
        inventory.addProduct("PROD-003", "Keyboard", "Mechanical keyboard", "Electronics", 79.99, "unit");
        inventory.addProduct("PROD-004", "Monitor", "27-inch 4K monitor", "Electronics", 299.99, "unit");
        inventory.addProduct("PROD-005", "Desk Chair", "Ergonomic office chair", "Furniture", 199.99, "unit");
        
        System.out.println("  Added 5 products");
        
        // Search products
        System.out.println("\nSearching for 'laptop':");
        List<Product> searchResults = inventory.searchProducts("laptop");
        searchResults.forEach(product -> System.out.println("  " + product));
        
        // Update product
        System.out.println("\nUpdating laptop price:");
        inventory.updateProduct("PROD-001", "Laptop", "High-performance laptop", "Electronics", 899.99, "unit");
        Product updatedProduct = inventory.getProduct("PROD-001");
        System.out.println("  Updated: " + updatedProduct);
        
        // Get all products
        System.out.println("\nAll products:");
        inventory.getAllProducts().forEach(product -> System.out.println("  " + product));
        
        System.out.println();
    }
    
    private static void demonstrateWarehouseManagement(InventoryManagementSystem inventory) {
        System.out.println("=== 2. Warehouse Management ===");
        
        // Add warehouses
        System.out.println("Adding warehouses:");
        inventory.addWarehouse("WH-001", "Main Warehouse", "New York", "123 Main St, NY", "555-0001");
        inventory.addWarehouse("WH-002", "Secondary Warehouse", "Los Angeles", "456 Oak Ave, LA", "555-0002");
        inventory.addWarehouse("WH-003", "Distribution Center", "Chicago", "789 Pine St, Chicago", "555-0003");
        
        System.out.println("  Added 3 warehouses");
        
        // Get warehouse info
        System.out.println("\nWarehouse details:");
        Warehouse mainWarehouse = inventory.getWarehouse("WH-001");
        System.out.println("  " + mainWarehouse);
        
        // Update warehouse
        System.out.println("\nUpdating main warehouse contact:");
        inventory.updateWarehouse("WH-001", "Main Warehouse", "New York", "123 Main St, NY", "555-0001-EXT");
        Warehouse updatedWarehouse = inventory.getWarehouse("WH-001");
        System.out.println("  Updated: " + updatedWarehouse);
        
        // Get all warehouses
        System.out.println("\nAll warehouses:");
        inventory.getAllWarehouses().forEach(warehouse -> System.out.println("  " + warehouse));
        
        System.out.println();
    }
    
    private static void demonstrateInventoryManagement(InventoryManagementSystem inventory) {
        System.out.println("=== 3. Inventory Management ===");
        
        // Add inventory items
        System.out.println("Adding inventory items:");
        inventory.addInventoryItem("ITEM-001", "PROD-001", "WH-001", 50, 10, 100); // Laptop in Main Warehouse
        inventory.addInventoryItem("ITEM-002", "PROD-001", "WH-002", 30, 5, 50);   // Laptop in Secondary Warehouse
        inventory.addInventoryItem("ITEM-003", "PROD-002", "WH-001", 200, 20, 500); // Mouse in Main Warehouse
        inventory.addInventoryItem("ITEM-004", "PROD-003", "WH-001", 100, 15, 200); // Keyboard in Main Warehouse
        inventory.addInventoryItem("ITEM-005", "PROD-004", "WH-002", 25, 5, 50);   // Monitor in Secondary Warehouse
        inventory.addInventoryItem("ITEM-006", "PROD-005", "WH-003", 15, 3, 30);   // Chair in Distribution Center
        
        System.out.println("  Added 6 inventory items");
        
        // Get inventory by product
        System.out.println("\nLaptop inventory across all warehouses:");
        List<InventoryItem> laptopItems = inventory.getInventoryItemsByProduct("PROD-001");
        laptopItems.forEach(item -> System.out.println("  " + item));
        
        // Get inventory by warehouse
        System.out.println("\nMain warehouse inventory:");
        List<InventoryItem> mainWarehouseItems = inventory.getInventoryItemsByWarehouse("WH-001");
        mainWarehouseItems.forEach(item -> System.out.println("  " + item));
        
        // Get specific inventory item
        System.out.println("\nLaptop in main warehouse:");
        InventoryItem laptopItem = inventory.getInventoryItem("ITEM-001");
        System.out.println("  " + laptopItem);
        
        System.out.println();
    }
    
    private static void demonstrateStockOperations(InventoryManagementSystem inventory) {
        System.out.println("=== 4. Stock Operations ===");
        
        // Add stock
        System.out.println("Adding 20 laptops to main warehouse:");
        boolean added = inventory.addStock("ITEM-001", 20, "New shipment received", "user001");
        System.out.println("  Stock added: " + added);
        
        InventoryItem updatedItem = inventory.getInventoryItem("ITEM-001");
        System.out.println("  Updated quantity: " + updatedItem.getQuantity());
        
        // Remove stock
        System.out.println("\nRemoving 5 laptops from main warehouse:");
        boolean removed = inventory.removeStock("ITEM-001", 5, "Order fulfillment", "user002");
        System.out.println("  Stock removed: " + removed);
        
        updatedItem = inventory.getInventoryItem("ITEM-001");
        System.out.println("  Updated quantity: " + updatedItem.getQuantity());
        
        // Transfer stock
        System.out.println("\nTransferring 10 laptops from main to secondary warehouse:");
        boolean transferred = inventory.transferStock("ITEM-001", "ITEM-002", 10, "Stock rebalancing", "user003");
        System.out.println("  Stock transferred: " + transferred);
        
        InventoryItem fromItem = inventory.getInventoryItem("ITEM-001");
        InventoryItem toItem = inventory.getInventoryItem("ITEM-002");
        System.out.println("  Main warehouse laptops: " + fromItem.getQuantity());
        System.out.println("  Secondary warehouse laptops: " + toItem.getQuantity());
        
        // Update quantity directly
        System.out.println("\nAdjusting keyboard quantity (inventory count):");
        boolean adjusted = inventory.updateInventoryQuantity("ITEM-004", 95, "Physical count adjustment", "user004");
        System.out.println("  Quantity adjusted: " + adjusted);
        
        InventoryItem keyboardItem = inventory.getInventoryItem("ITEM-004");
        System.out.println("  Updated keyboard quantity: " + keyboardItem.getQuantity());
        
        // Show recent transactions
        System.out.println("\nRecent transactions:");
        List<Transaction> recentTransactions = inventory.getAllTransactions();
        recentTransactions.stream()
                .sorted((t1, t2) -> t2.getTimestamp().compareTo(t1.getTimestamp()))
                .limit(5)
                .forEach(transaction -> System.out.println("  " + transaction));
        
        System.out.println();
    }
    
    private static void demonstrateAlertsAndReporting(InventoryManagementSystem inventory) {
        System.out.println("=== 5. Alerts and Reporting ===");
        
        // Create low stock scenario
        System.out.println("Creating low stock scenario:");
        inventory.updateInventoryQuantity("ITEM-003", 5, "Sales depletion", "user005");
        inventory.updateInventoryQuantity("ITEM-005", 2, "High demand", "user005");
        inventory.updateInventoryQuantity("ITEM-006", 0, "Sold out", "user005");
        
        // Check alerts
        System.out.println("\nStock alerts:");
        List<StockAlert> alerts = inventory.getUnresolvedStockAlerts();
        alerts.forEach(alert -> System.out.println("  " + alert));
        
        // System statistics
        System.out.println("\nSystem statistics:");
        SystemStats stats = inventory.getSystemStats();
        System.out.println("  " + stats);
        
        // Inventory value
        System.out.println("\nTotal inventory value: $" + String.format("%.2f", inventory.getTotalInventoryValue()));
        
        // Inventory value by warehouse
        System.out.println("\nInventory value by warehouse:");
        Map<String, Double> warehouseValues = inventory.getInventoryValueByWarehouse();
        warehouseValues.forEach((warehouseId, value) -> 
            System.out.println("  " + warehouseId + ": $" + String.format("%.2f", value)));
        
        // Low stock items
        System.out.println("\nLow stock items:");
        List<InventoryItem> lowStockItems = inventory.getLowStockItems();
        lowStockItems.forEach(item -> System.out.println("  " + item));
        
        // Out of stock items
        System.out.println("\nOut of stock items:");
        List<InventoryItem> outOfStockItems = inventory.getOutOfStockItems();
        outOfStockItems.forEach(item -> System.out.println("  " + item));
        
        // Transactions by type
        System.out.println("\nStock-in transactions:");
        List<Transaction> stockInTransactions = inventory.getTransactionsByType(TransactionType.STOCK_IN);
        stockInTransactions.forEach(transaction -> System.out.println("  " + transaction));
        
        // Resolve some alerts
        System.out.println("\nResolving alerts:");
        List<StockAlert> unresolvedAlerts = inventory.getUnresolvedStockAlerts();
        if (!unresolvedAlerts.isEmpty()) {
            String alertId = unresolvedAlerts.get(0).getAlertId();
            boolean resolved = inventory.resolveStockAlert(alertId);
            System.out.println("  Alert " + alertId + " resolved: " + resolved);
        }
        
        System.out.println("\n=== Demo Completed Successfully! ===");
    }
}
