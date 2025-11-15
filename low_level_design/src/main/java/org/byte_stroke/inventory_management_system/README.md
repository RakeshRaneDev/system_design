# Simple Inventory Management System

A lightweight, non-concurrent inventory management system that provides comprehensive product management, inventory tracking, stock operations, transaction logging, and reporting capabilities.

## Features

### Core Features
- **Product Management** - CRUD operations for products
- **Warehouse Management** - Multi-warehouse support
- **Inventory Tracking** - Real-time stock levels and monitoring
- **Stock Operations** - Add, remove, transfer, and adjust stock
- **Transaction Logging** - Complete audit trail of all operations
- **Alert System** - Low stock, out of stock, and overstock alerts
- **Reporting** - Comprehensive analytics and statistics

### Advanced Features
- **Multi-warehouse Support** - Track inventory across multiple locations
- **Stock Transfers** - Move inventory between warehouses
- **Alert Management** - Automated alerts with resolution tracking
- **Search and Filtering** - Find products, transactions, and inventory items
- **Value Calculations** - Total inventory value and warehouse-specific values
- **Transaction History** - Complete audit trail with filtering options

## Quick Start

```java
// Create inventory system
SimpleInventoryManagementSystem inventory = new SimpleInventoryManagementSystem("my-inventory");

// Add products
inventory.addProduct("PROD-001", "Laptop", "High-performance laptop", "Electronics", 999.99, "unit");

// Add warehouses
inventory.addWarehouse("WH-001", "Main Warehouse", "New York", "123 Main St, NY", "555-0001");

// Add inventory items
inventory.addInventoryItem("ITEM-001", "PROD-001", "WH-001", 50, 10, 100);

// Stock operations
inventory.addStock("ITEM-001", 20, "New shipment", "user001");
inventory.removeStock("ITEM-001", 5, "Order fulfillment", "user002");

// Get reports
double totalValue = inventory.getTotalInventoryValue();
List<SimpleStockAlert> alerts = inventory.getUnresolvedStockAlerts();
```

## API Reference

### Product Management

#### Add Product
```java
boolean addProduct(String productId, String name, String description, String category, 
                  double unitPrice, String unit)
```

#### Update Product
```java
boolean updateProduct(String productId, String name, String description, String category, 
                     double unitPrice, String unit)
```

#### Remove Product
```java
boolean removeProduct(String productId)
```

#### Get Product
```java
SimpleProduct getProduct(String productId)
List<SimpleProduct> getAllProducts()
List<SimpleProduct> searchProducts(String searchTerm)
```

### Warehouse Management

#### Add Warehouse
```java
boolean addWarehouse(String warehouseId, String name, String location, String address, String contactInfo)
```

#### Update Warehouse
```java
boolean updateWarehouse(String warehouseId, String name, String location, String address, String contactInfo)
```

#### Remove Warehouse
```java
boolean removeWarehouse(String warehouseId)
```

#### Get Warehouse
```java
SimpleWarehouse getWarehouse(String warehouseId)
List<SimpleWarehouse> getAllWarehouses()
```

### Inventory Management

#### Add Inventory Item
```java
boolean addInventoryItem(String itemId, String productId, String warehouseId, 
                        int quantity, int minStockLevel, int maxStockLevel)
```

#### Stock Operations
```java
// Add stock
boolean addStock(String itemId, int quantity, String reason, String userId)

// Remove stock
boolean removeStock(String itemId, int quantity, String reason, String userId)

// Update quantity
boolean updateInventoryQuantity(String itemId, int newQuantity, String reason, String userId)

// Transfer stock
boolean transferStock(String fromItemId, String toItemId, int quantity, String reason, String userId)
```

#### Get Inventory
```java
SimpleInventoryItem getInventoryItem(String itemId)
List<SimpleInventoryItem> getAllInventoryItems()
List<SimpleInventoryItem> getInventoryItemsByProduct(String productId)
List<SimpleInventoryItem> getInventoryItemsByWarehouse(String warehouseId)
```

### Transaction Management

#### Get Transactions
```java
List<SimpleTransaction> getAllTransactions()
List<SimpleTransaction> getTransactionsByProduct(String productId)
List<SimpleTransaction> getTransactionsByWarehouse(String warehouseId)
List<SimpleTransaction> getTransactionsByType(TransactionType type)
List<SimpleTransaction> getTransactionsByDateRange(LocalDateTime startDate, LocalDateTime endDate)
```

#### Transaction Types
```java
enum TransactionType {
    STOCK_IN("Stock In"),
    STOCK_OUT("Stock Out"),
    ADJUSTMENT("Adjustment"),
    TRANSFER_IN("Transfer In"),
    TRANSFER_OUT("Transfer Out"),
    RETURN("Return"),
    DAMAGE("Damage"),
    EXPIRY("Expiry")
}
```

### Alert Management

#### Get Alerts
```java
List<SimpleStockAlert> getAllStockAlerts()
List<SimpleStockAlert> getUnresolvedStockAlerts()
```

#### Resolve Alert
```java
boolean resolveStockAlert(String alertId)
```

#### Alert Types
```java
enum AlertType {
    LOW_STOCK("Low Stock"),
    OUT_OF_STOCK("Out of Stock"),
    OVERSTOCK("Overstock"),
    EXPIRY_WARNING("Expiry Warning"),
    EXPIRED("Expired")
}
```

### Reporting and Analytics

#### Inventory Reports
```java
// Total inventory value
double getTotalInventoryValue()

// Inventory value by warehouse
Map<String, Double> getInventoryValueByWarehouse()

// Stock level reports
List<SimpleInventoryItem> getLowStockItems()
List<SimpleInventoryItem> getOutOfStockItems()
List<SimpleInventoryItem> getOverstockedItems()

// System statistics
SystemStats getSystemStats()
```

## Data Models

### SimpleProduct
```java
public class SimpleProduct {
    private final String productId;
    private final String name;
    private final String description;
    private final String category;
    private final double unitPrice;
    private final String unit;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
```

### SimpleWarehouse
```java
public class SimpleWarehouse {
    private final String warehouseId;
    private final String name;
    private final String location;
    private final String address;
    private final String contactInfo;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
```

### SimpleInventoryItem
```java
public class SimpleInventoryItem {
    private final String itemId;
    private final String productId;
    private final String warehouseId;
    private int quantity;
    private final int minStockLevel;
    private final int maxStockLevel;
    private final LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;
}
```

### SimpleTransaction
```java
public class SimpleTransaction {
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
}
```

### SimpleStockAlert
```java
public class SimpleStockAlert {
    private final String alertId;
    private final String itemId;
    private final String productId;
    private final String warehouseId;
    private final AlertType type;
    private final String message;
    private final int currentQuantity;
    private final int threshold;
    private final LocalDateTime createdAt;
    private boolean isResolved;
    private final LocalDateTime resolvedAt;
}
```

## Examples

### E-commerce Inventory Management
```java
SimpleInventoryManagementSystem inventory = new SimpleInventoryManagementSystem("ecommerce-inventory");

// Setup products
inventory.addProduct("LAPTOP-001", "MacBook Pro", "Apple MacBook Pro 16-inch", "Electronics", 2499.99, "unit");
inventory.addProduct("MOUSE-001", "Magic Mouse", "Apple Magic Mouse", "Electronics", 79.99, "unit");

// Setup warehouses
inventory.addWarehouse("NY-WH", "New York Warehouse", "New York", "123 Tech St, NY", "555-0001");
inventory.addWarehouse("CA-WH", "California Warehouse", "Los Angeles", "456 Silicon Ave, CA", "555-0002");

// Add inventory
inventory.addInventoryItem("ITEM-001", "LAPTOP-001", "NY-WH", 100, 20, 200);
inventory.addInventoryItem("ITEM-002", "MOUSE-001", "NY-WH", 500, 50, 1000);

// Stock operations
inventory.addStock("ITEM-001", 50, "New shipment from supplier", "admin001");
inventory.removeStock("ITEM-001", 10, "Online order fulfillment", "user001");

// Check alerts
List<SimpleStockAlert> alerts = inventory.getUnresolvedStockAlerts();
alerts.forEach(alert -> System.out.println("Alert: " + alert.getMessage()));

// Get reports
double totalValue = inventory.getTotalInventoryValue();
System.out.println("Total inventory value: $" + totalValue);
```

### Multi-Warehouse Stock Transfer
```java
// Transfer stock between warehouses
inventory.transferStock("ITEM-001", "ITEM-003", 20, "Stock rebalancing", "manager001");

// Check transfer transactions
List<SimpleTransaction> transfers = inventory.getTransactionsByType(SimpleTransaction.TransactionType.TRANSFER_OUT);
transfers.forEach(transaction -> System.out.println("Transfer: " + transaction));
```

### Low Stock Monitoring
```java
// Check for low stock items
List<SimpleInventoryItem> lowStockItems = inventory.getLowStockItems();
lowStockItems.forEach(item -> {
    System.out.println("Low stock: " + item.getProductId() + " - " + item.getQuantity() + " remaining");
});

// Get unresolved alerts
List<SimpleStockAlert> unresolvedAlerts = inventory.getUnresolvedStockAlerts();
unresolvedAlerts.forEach(alert -> {
    System.out.println("Alert: " + alert.getMessage());
    // Resolve alert after restocking
    inventory.resolveStockAlert(alert.getAlertId());
});
```

### Transaction History Analysis
```java
// Get transactions by date range
LocalDateTime startDate = LocalDateTime.now().minusDays(30);
LocalDateTime endDate = LocalDateTime.now();
List<SimpleTransaction> recentTransactions = inventory.getTransactionsByDateRange(startDate, endDate);

// Analyze transaction types
Map<SimpleTransaction.TransactionType, Long> transactionCounts = recentTransactions.stream()
    .collect(Collectors.groupingBy(SimpleTransaction::getType, Collectors.counting()));

transactionCounts.forEach((type, count) -> 
    System.out.println(type.getDescription() + ": " + count + " transactions"));
```

## Performance Considerations

### Memory Usage
- All data is stored in memory using HashMap and ArrayList
- Consider memory limits for large datasets
- Each product, warehouse, and inventory item has metadata overhead

### No Concurrency
- Single-threaded operations only
- No thread safety mechanisms
- Suitable for single-threaded applications or small-scale systems

### Scalability
- Linear search for complex queries
- No indexing for large datasets
- Consider database solutions for production use

## Limitations

- **No concurrency support** - Single-threaded only
- **Memory-based storage** - Data not persistent across restarts
- **No ACID transactions** - No rollback capabilities
- **No complex queries** - Basic filtering only
- **No indexing** - Linear search for complex operations
- **No persistence** - Data lost on system restart

## Best Practices

1. **Regular backups** - Export data periodically
2. **Monitor memory usage** - For large datasets
3. **Use appropriate stock levels** - Set realistic min/max levels
4. **Regular alert resolution** - Keep alert system clean
5. **Transaction logging** - Maintain complete audit trail
6. **Data validation** - Validate inputs before operations
7. **Error handling** - Handle exceptions appropriately

## Comparison with Concurrent System

| Feature | Simple System | Concurrent System |
|---------|---------------|-------------------|
| **Thread Safety** | ❌ No | ✅ Yes |
| **Performance** | ✅ Faster | ⚠️ Slower (locks) |
| **Memory Usage** | ✅ Lower | ⚠️ Higher (locks) |
| **Complexity** | ✅ Simple | ⚠️ Complex |
| **Use Case** | Single-threaded apps | Multi-threaded apps |
| **Scalability** | ⚠️ Limited | ✅ Better |

## Future Enhancements

- Persistent storage options
- Database integration
- Advanced reporting and analytics
- Barcode/QR code support
- API endpoints for external integration
- Real-time notifications
- Mobile app support
- Cloud deployment options
