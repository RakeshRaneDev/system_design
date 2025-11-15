package org.byte_stroke.inventory_management_system;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a product in the inventory system
 */
public class Product {
    private final String productId;
    private final String name;
    private final String description;
    private final String category;
    private final double unitPrice;
    private final String unit;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public Product(String productId, String name, String description, String category,
                   double unitPrice, String unit) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.unitPrice = unitPrice;
        this.unit = unit;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Product(String productId, String name, String description, String category,
                   double unitPrice, String unit, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.unitPrice = unitPrice;
        this.unit = unit;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters
    public String getProductId() { return productId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public double getUnitPrice() { return unitPrice; }
    public String getUnit() { return unit; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // Create updated product
    public Product withUpdatedInfo(String name, String description, String category,
                                   double unitPrice, String unit) {
        return new Product(productId, name, description, category, unitPrice, unit,
                               createdAt, LocalDateTime.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product that = (Product) o;
        return Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }

    @Override
    public String toString() {
        return "SimpleProduct{" +
                "productId='" + productId + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", unitPrice=" + unitPrice +
                ", unit='" + unit + '\'' +
                '}';
    }
}
