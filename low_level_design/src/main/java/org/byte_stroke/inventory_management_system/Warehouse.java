package org.byte_stroke.inventory_management_system;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a warehouse in the inventory system
 */
public class Warehouse {
    private final String warehouseId;
    private final String name;
    private final String location;
    private final String address;
    private final String contactInfo;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public Warehouse(String warehouseId, String name, String location, String address, String contactInfo) {
        this.warehouseId = warehouseId;
        this.name = name;
        this.location = location;
        this.address = address;
        this.contactInfo = contactInfo;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Warehouse(String warehouseId, String name, String location, String address,
                     String contactInfo, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.warehouseId = warehouseId;
        this.name = name;
        this.location = location;
        this.address = address;
        this.contactInfo = contactInfo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters
    public String getWarehouseId() { return warehouseId; }
    public String getName() { return name; }
    public String getLocation() { return location; }
    public String getAddress() { return address; }
    public String getContactInfo() { return contactInfo; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // Create updated warehouse
    public Warehouse withUpdatedInfo(String name, String location, String address, String contactInfo) {
        return new Warehouse(warehouseId, name, location, address, contactInfo,
                                 createdAt, LocalDateTime.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Warehouse that = (Warehouse) o;
        return Objects.equals(warehouseId, that.warehouseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(warehouseId);
    }

    @Override
    public String toString() {
        return "SimpleWarehouse{" +
                "warehouseId='" + warehouseId + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
