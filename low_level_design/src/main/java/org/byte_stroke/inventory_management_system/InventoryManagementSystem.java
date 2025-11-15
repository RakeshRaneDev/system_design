package org.byte_stroke.inventory_management_system;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Simple Inventory Management System without concurrency
 * Provides product management, inventory tracking, transaction logging, and reporting
 */
public class InventoryManagementSystem {
    private final Map<String, Product> products;
    private final Map<String, Warehouse> warehouses;
    private final Map<String, InventoryItem> inventoryItems;
    private final List<Transaction> transactions;
    private final List<StockAlert> stockAlerts;
    private final String systemId;
    private long transactionIdCounter;
    private long alertIdCounter;

    public InventoryManagementSystem() {
        this("inventory-system");
    }

    public InventoryManagementSystem(String systemId) {
        this.systemId = systemId;
        this.products = new HashMap<>();
        this.warehouses = new HashMap<>();
        this.inventoryItems = new HashMap<>();
        this.transactions = new ArrayList<>();
        this.stockAlerts = new ArrayList<>();
        this.transactionIdCounter = 1;
        this.alertIdCounter = 1;
    }

    // ========== Product Management ==========

    /**
     * Adds a new product to the system
     */
    public boolean addProduct(String productId, String name, String description, String category,
                            double unitPrice, String unit) {
        if (products.containsKey(productId)) {
            return false; // Product already exists
        }

        Product product = new Product(productId, name, description, category, unitPrice, unit);
        products.put(productId, product);
        return true;
    }

    /**
     * Updates an existing product
     */
    public boolean updateProduct(String productId, String name, String description, String category,
                               double unitPrice, String unit) {
        Product existingProduct = products.get(productId);
        if (existingProduct == null) {
            return false; // Product doesn't exist
        }

        Product updatedProduct = existingProduct.withUpdatedInfo(name, description, category, unitPrice, unit);
        products.put(productId, updatedProduct);
        return true;
    }

    /**
     * Removes a product from the system
     */
    public boolean removeProduct(String productId) {
        // Check if product has inventory items
        boolean hasInventory = inventoryItems.values().stream()
                .anyMatch(item -> item.getProductId().equals(productId));
        
        if (hasInventory) {
            return false; // Cannot remove product with existing inventory
        }

        return products.remove(productId) != null;
    }

    /**
     * Gets a product by ID
     */
    public Product getProduct(String productId) {
        return products.get(productId);
    }

    /**
     * Gets all products
     */
    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    /**
     * Searches products by name or category
     */
    public List<Product> searchProducts(String searchTerm) {
        return products.values().stream()
                .filter(product -> product.getName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                                 product.getCategory().toLowerCase().contains(searchTerm.toLowerCase()) ||
                                 product.getDescription().toLowerCase().contains(searchTerm.toLowerCase()))
                .collect(Collectors.toList());
    }

    // ========== Warehouse Management ==========

    /**
     * Adds a new warehouse
     */
    public boolean addWarehouse(String warehouseId, String name, String location, String address, String contactInfo) {
        if (warehouses.containsKey(warehouseId)) {
            return false; // Warehouse already exists
        }

        Warehouse warehouse = new Warehouse(warehouseId, name, location, address, contactInfo);
        warehouses.put(warehouseId, warehouse);
        return true;
    }

    /**
     * Updates an existing warehouse
     */
    public boolean updateWarehouse(String warehouseId, String name, String location, String address, String contactInfo) {
        Warehouse existingWarehouse = warehouses.get(warehouseId);
        if (existingWarehouse == null) {
            return false; // Warehouse doesn't exist
        }

        Warehouse updatedWarehouse = existingWarehouse.withUpdatedInfo(name, location, address, contactInfo);
        warehouses.put(warehouseId, updatedWarehouse);
        return true;
    }

    /**
     * Removes a warehouse
     */
    public boolean removeWarehouse(String warehouseId) {
        // Check if warehouse has inventory items
        boolean hasInventory = inventoryItems.values().stream()
                .anyMatch(item -> item.getWarehouseId().equals(warehouseId));
        
        if (hasInventory) {
            return false; // Cannot remove warehouse with existing inventory
        }

        return warehouses.remove(warehouseId) != null;
    }

    /**
     * Gets a warehouse by ID
     */
    public Warehouse getWarehouse(String warehouseId) {
        return warehouses.get(warehouseId);
    }

    /**
     * Gets all warehouses
     */
    public List<Warehouse> getAllWarehouses() {
        return new ArrayList<>(warehouses.values());
    }

    // ========== Inventory Management ==========

    /**
     * Adds inventory item to a warehouse
     */
    public boolean addInventoryItem(String itemId, String productId, String warehouseId,
                                  int quantity, int minStockLevel, int maxStockLevel) {
        if (inventoryItems.containsKey(itemId)) {
            return false; // Item already exists
        }

        if (!products.containsKey(productId) || !warehouses.containsKey(warehouseId)) {
            return false; // Product or warehouse doesn't exist
        }

        InventoryItem item = new InventoryItem(itemId, productId, warehouseId,
                                                          quantity, minStockLevel, maxStockLevel);
        inventoryItems.put(itemId, item);
        
        // Log transaction
        logTransaction(itemId, productId, warehouseId,TransactionType.STOCK_IN,
                     quantity, "Initial stock", "system", "Initial inventory setup");
        
        // Check for alerts
        checkAndCreateAlerts(item);
        
        return true;
    }

    /**
     * Updates inventory quantity
     */
    public boolean updateInventoryQuantity(String itemId, int newQuantity, String reason, String userId) {
        InventoryItem item = inventoryItems.get(itemId);
        if (item == null) {
            return false; // Item doesn't exist
        }

        int oldQuantity = item.getQuantity();
        int quantityChange = newQuantity - oldQuantity;
        
        InventoryItem updatedItem = item.updateQuantity(newQuantity);
        inventoryItems.put(itemId, updatedItem);
        
        // Log transaction
        TransactionType transactionType = quantityChange > 0 ?
            TransactionType.ADJUSTMENT : TransactionType.ADJUSTMENT;
        
        logTransaction(itemId, item.getProductId(), item.getWarehouseId(), transactionType, 
                     Math.abs(quantityChange), reason, userId, "Quantity adjustment");
        
        // Check for alerts
        checkAndCreateAlerts(updatedItem);
        
        return true;
    }

    /**
     * Adds stock to inventory
     */
    public boolean addStock(String itemId, int quantity, String reason, String userId) {
        InventoryItem item = inventoryItems.get(itemId);
        if (item == null) {
            return false; // Item doesn't exist
        }

        InventoryItem updatedItem = item.addQuantity(quantity);
        inventoryItems.put(itemId, updatedItem);
        
        // Log transaction
        logTransaction(itemId, item.getProductId(), item.getWarehouseId(), 
                     TransactionType.STOCK_IN, quantity, reason, userId, "Stock added");
        
        // Check for alerts
        checkAndCreateAlerts(updatedItem);
        
        return true;
    }

    /**
     * Removes stock from inventory
     */
    public boolean removeStock(String itemId, int quantity, String reason, String userId) {
        InventoryItem item = inventoryItems.get(itemId);
        if (item == null) {
            return false; // Item doesn't exist
        }

        if (item.getQuantity() < quantity) {
            return false; // Insufficient stock
        }

        InventoryItem updatedItem = item.removeQuantity(quantity);
        inventoryItems.put(itemId, updatedItem);
        
        // Log transaction
        logTransaction(itemId, item.getProductId(), item.getWarehouseId(), 
                     TransactionType.STOCK_OUT, quantity, reason, userId, "Stock removed");
        
        // Check for alerts
        checkAndCreateAlerts(updatedItem);
        
        return true;
    }

    /**
     * Transfers stock between warehouses
     */
    public boolean transferStock(String fromItemId, String toItemId, int quantity, String reason, String userId) {
        InventoryItem fromItem = inventoryItems.get(fromItemId);
        InventoryItem toItem = inventoryItems.get(toItemId);
        
        if (fromItem == null || toItem == null) {
            return false; // Items don't exist
        }

        if (!fromItem.getProductId().equals(toItem.getProductId())) {
            return false; // Different products
        }

        if (fromItem.getQuantity() < quantity) {
            return false; // Insufficient stock
        }

        // Remove from source
        InventoryItem updatedFromItem = fromItem.removeQuantity(quantity);
        inventoryItems.put(fromItemId, updatedFromItem);
        
        // Add to destination
        InventoryItem updatedToItem = toItem.addQuantity(quantity);
        inventoryItems.put(toItemId, updatedToItem);
        
        // Log transactions
        logTransaction(fromItemId, fromItem.getProductId(), fromItem.getWarehouseId(), 
                     TransactionType.TRANSFER_OUT, quantity, reason, userId, "Transfer out");
        
        logTransaction(toItemId, toItem.getProductId(), toItem.getWarehouseId(), 
                     TransactionType.TRANSFER_IN, quantity, reason, userId, "Transfer in");
        
        // Check for alerts
        checkAndCreateAlerts(updatedFromItem);
        checkAndCreateAlerts(updatedToItem);
        
        return true;
    }

    /**
     * Gets inventory item by ID
     */
    public InventoryItem getInventoryItem(String itemId) {
        return inventoryItems.get(itemId);
    }

    /**
     * Gets all inventory items
     */
    public List<InventoryItem> getAllInventoryItems() {
        return new ArrayList<>(inventoryItems.values());
    }

    /**
     * Gets inventory items by product
     */
    public List<InventoryItem> getInventoryItemsByProduct(String productId) {
        return inventoryItems.values().stream()
                .filter(item -> item.getProductId().equals(productId))
                .collect(Collectors.toList());
    }

    /**
     * Gets inventory items by warehouse
     */
    public List<InventoryItem> getInventoryItemsByWarehouse(String warehouseId) {
        return inventoryItems.values().stream()
                .filter(item -> item.getWarehouseId().equals(warehouseId))
                .collect(Collectors.toList());
    }

    // ========== Transaction Management ==========

    /**
     * Logs a transaction
     */
    private void logTransaction(String itemId, String productId, String warehouseId,
                                TransactionType type, int quantity, String reason,
                                String userId, String notes) {
        String transactionId = "TXN-" + String.format("%06d", transactionIdCounter++);
        Transaction transaction = new Transaction(transactionId, itemId, productId, warehouseId,
                                                            type, quantity, reason, userId, notes);
        transactions.add(transaction);
    }

    /**
     * Gets all transactions
     */
    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }

    /**
     * Gets transactions by product
     */
    public List<Transaction> getTransactionsByProduct(String productId) {
        return transactions.stream()
                .filter(transaction -> transaction.getProductId().equals(productId))
                .collect(Collectors.toList());
    }

    /**
     * Gets transactions by warehouse
     */
    public List<Transaction> getTransactionsByWarehouse(String warehouseId) {
        return transactions.stream()
                .filter(transaction -> transaction.getWarehouseId().equals(warehouseId))
                .collect(Collectors.toList());
    }

    /**
     * Gets transactions by type
     */
    public List<Transaction> getTransactionsByType(TransactionType type) {
        return transactions.stream()
                .filter(transaction -> transaction.getType() == type)
                .collect(Collectors.toList());
    }

    /**
     * Gets transactions within date range
     */
    public List<Transaction> getTransactionsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return transactions.stream()
                .filter(transaction -> !transaction.getTimestamp().isBefore(startDate) && 
                                     !transaction.getTimestamp().isAfter(endDate))
                .collect(Collectors.toList());
    }

    // ========== Alert Management ==========

    /**
     * Checks and creates alerts for inventory item
     */
    private void checkAndCreateAlerts(InventoryItem item) {
        // Check for low stock
        if (item.isLowStock() && !item.isOutOfStock()) {
            createAlert(item, AlertType.LOW_STOCK,
                      "Low stock alert: " + item.getQuantity() + " remaining", item.getMinStockLevel());
        }
        
        // Check for out of stock
        if (item.isOutOfStock()) {
            createAlert(item, AlertType.OUT_OF_STOCK,
                      "Out of stock: " + item.getQuantity() + " remaining", 0);
        }
        
        // Check for overstock
        if (item.isOverstocked()) {
            createAlert(item, AlertType.OVERSTOCK,
                      "Overstock alert: " + item.getQuantity() + " exceeds max level", item.getMaxStockLevel());
        }
    }

    /**
     * Creates a stock alert
     */
    private void createAlert(InventoryItem item, AlertType type,
                             String message, int threshold) {
        String alertId = "ALT-" + String.format("%06d", alertIdCounter++);
        StockAlert alert = new StockAlert(alertId, item.getItemId(), item.getProductId(),
                                                    item.getWarehouseId(), type, message, 
                                                    item.getQuantity(), threshold);
        stockAlerts.add(alert);
    }

    /**
     * Gets all stock alerts
     */
    public List<StockAlert> getAllStockAlerts() {
        return new ArrayList<>(stockAlerts);
    }

    /**
     * Gets unresolved stock alerts
     */
    public List<StockAlert> getUnresolvedStockAlerts() {
        return stockAlerts.stream()
                .filter(alert -> !alert.isResolved())
                .collect(Collectors.toList());
    }

    /**
     * Resolves a stock alert
     */
    public boolean resolveStockAlert(String alertId) {
        for (int i = 0; i < stockAlerts.size(); i++) {
            StockAlert alert = stockAlerts.get(i);
            if (alert.getAlertId().equals(alertId) && !alert.isResolved()) {
                stockAlerts.set(i, alert.resolve());
                return true;
            }
        }
        return false;
    }

    // ========== Reporting and Analytics ==========

    /**
     * Gets total inventory value
     */
    public double getTotalInventoryValue() {
        return inventoryItems.values().stream()
                .mapToDouble(item -> {
                    Product product = products.get(item.getProductId());
                    return product != null ? item.getQuantity() * product.getUnitPrice() : 0;
                })
                .sum();
    }

    /**
     * Gets inventory value by warehouse
     */
    public Map<String, Double> getInventoryValueByWarehouse() {
        Map<String, Double> warehouseValues = new HashMap<>();
        
        for (InventoryItem item : inventoryItems.values()) {
            Product product = products.get(item.getProductId());
            if (product != null) {
                double value = item.getQuantity() * product.getUnitPrice();
                warehouseValues.merge(item.getWarehouseId(), value, Double::sum);
            }
        }
        
        return warehouseValues;
    }

    /**
     * Gets low stock items
     */
    public List<InventoryItem> getLowStockItems() {
        return inventoryItems.values().stream()
                .filter(InventoryItem::isLowStock)
                .collect(Collectors.toList());
    }

    /**
     * Gets out of stock items
     */
    public List<InventoryItem> getOutOfStockItems() {
        return inventoryItems.values().stream()
                .filter(InventoryItem::isOutOfStock)
                .collect(Collectors.toList());
    }

    /**
     * Gets overstocked items
     */
    public List<InventoryItem> getOverstockedItems() {
        return inventoryItems.values().stream()
                .filter(InventoryItem::isOverstocked)
                .collect(Collectors.toList());
    }

    /**
     * Gets system statistics
     */
    public SystemStats getSystemStats() {
        int totalProducts = products.size();
        int totalWarehouses = warehouses.size();
        int totalInventoryItems = inventoryItems.size();
        int totalTransactions = transactions.size();
        int totalAlerts = stockAlerts.size();
        int unresolvedAlerts = (int) stockAlerts.stream().filter(alert -> !alert.isResolved()).count();
        double totalValue = getTotalInventoryValue();
        
        return new SystemStats(systemId, totalProducts, totalWarehouses, totalInventoryItems, 
                             totalTransactions, totalAlerts, unresolvedAlerts, totalValue);
    }
}
