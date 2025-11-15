package org.byte_stroke.inventory_management_system;


/**
 * System statistics
 */
public  class SystemStats {
    private final String systemId;
    private final int totalProducts;
    private final int totalWarehouses;
    private final int totalInventoryItems;
    private final int totalTransactions;
    private final int totalAlerts;
    private final int unresolvedAlerts;
    private final double totalInventoryValue;

    public SystemStats(String systemId, int totalProducts, int totalWarehouses, int totalInventoryItems,
                       int totalTransactions, int totalAlerts, int unresolvedAlerts, double totalInventoryValue) {
        this.systemId = systemId;
        this.totalProducts = totalProducts;
        this.totalWarehouses = totalWarehouses;
        this.totalInventoryItems = totalInventoryItems;
        this.totalTransactions = totalTransactions;
        this.totalAlerts = totalAlerts;
        this.unresolvedAlerts = unresolvedAlerts;
        this.totalInventoryValue = totalInventoryValue;
    }

    public String getSystemId() { return systemId; }
    public int getTotalProducts() { return totalProducts; }
    public int getTotalWarehouses() { return totalWarehouses; }
    public int getTotalInventoryItems() { return totalInventoryItems; }
    public int getTotalTransactions() { return totalTransactions; }
    public int getTotalAlerts() { return totalAlerts; }
    public int getUnresolvedAlerts() { return unresolvedAlerts; }
    public double getTotalInventoryValue() { return totalInventoryValue; }

    @Override
    public String toString() {
        return String.format("SystemStats{systemId='%s', products=%d, warehouses=%d, items=%d, " +
                        "transactions=%d, alerts=%d, unresolved=%d, value=%.2f}",
                systemId, totalProducts, totalWarehouses, totalInventoryItems,
                totalTransactions, totalAlerts, unresolvedAlerts, totalInventoryValue);
    }
}
