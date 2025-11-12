package org.byte_stroke.inventory_management_system;

import java.util.List;

/**
 * Simple test runner for the Simple Inventory Management System
 */
public class InventoryTestRunner {
    public static void main(String[] args) {
        System.out.println("=== Simple Inventory System Test Runner ===\n");
        
        // Create inventory system
        InventoryManagementSystem inventory = new InventoryManagementSystem("test-inventory");
        
        try {
            // Test 1: Basic Setup
            System.out.println("1. Setting up products and warehouses:");
            
            // Add products
            inventory.addProduct("PROD-001", "Laptop", "High-performance laptop", "Electronics", 999.99, "unit");
            inventory.addProduct("PROD-002", "Mouse", "Wireless mouse", "Electronics", 29.99, "unit");
            inventory.addProduct("PROD-003", "Keyboard", "Mechanical keyboard", "Electronics", 79.99, "unit");
            
            // Add warehouses
            inventory.addWarehouse("WH-001", "Main Warehouse", "New York", "123 Main St, NY", "555-0001");
            inventory.addWarehouse("WH-002", "Secondary Warehouse", "Los Angeles", "456 Oak Ave, LA", "555-0002");
            
            System.out.println("   Added 3 products and 2 warehouses");
            
            // Test 2: Inventory Management
            System.out.println("\n2. Adding inventory items:");
            
            inventory.addInventoryItem("ITEM-001", "PROD-001", "WH-001", 50, 10, 100);
            inventory.addInventoryItem("ITEM-002", "PROD-002", "WH-001", 200, 20, 500);
            inventory.addInventoryItem("ITEM-003", "PROD-003", "WH-002", 100, 15, 200);
            
            System.out.println("   Added 3 inventory items");
            
            // Test 3: Stock Operations
            System.out.println("\n3. Testing stock operations:");
            
            // Add stock
            boolean added = inventory.addStock("ITEM-001", 20, "New shipment", "user001");
            System.out.println("   Added stock: " + added);
            
            // Remove stock
            boolean removed = inventory.removeStock("ITEM-001", 10, "Order fulfillment", "user002");
            System.out.println("   Removed stock: " + removed);
            
            // Update quantity
            boolean updated = inventory.updateInventoryQuantity("ITEM-002", 150, "Physical count", "user003");
            System.out.println("   Updated quantity: " + updated);
            
            // Test 4: Alerts
            System.out.println("\n4. Testing alerts:");
            
            // Create low stock scenario
            inventory.updateInventoryQuantity("ITEM-002", 5, "Sales depletion", "user004");
            
            List<StockAlert> alerts = inventory.getUnresolvedStockAlerts();
            System.out.println("   Generated " + alerts.size() + " alerts");
            
            // Test 5: Reporting
            System.out.println("\n5. System reporting:");
            
            SystemStats stats = inventory.getSystemStats();
            System.out.println("   " + stats);
            
            System.out.println("   Total inventory value: $" + String.format("%.2f", inventory.getTotalInventoryValue()));
            
            // Test 6: Search and Filter
            System.out.println("\n6. Testing search and filter:");
            
            List<Product> searchResults = inventory.searchProducts("laptop");
            System.out.println("   Found " + searchResults.size() + " products matching 'laptop'");
            
            List<InventoryItem> lowStockItems = inventory.getLowStockItems();
            System.out.println("   Found " + lowStockItems.size() + " low stock items");
            
            List<Transaction> transactions = inventory.getAllTransactions();
            System.out.println("   Total transactions: " + transactions.size());
            
            System.out.println("\n=== All Tests Completed Successfully! ===");
            
        } catch (Exception e) {
            System.err.println("Error during testing: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
