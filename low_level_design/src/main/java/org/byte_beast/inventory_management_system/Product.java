package org.byte_beast.inventory_management_system;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

public class Product {
    private final String productId;
    private final String category;
    private final String unit;

    private final String unitPrice;

    private final String description;


    private  final LocalDateTime createdOn;
    private final  LocalDateTime updatedOn;

    public Product(String productId, String category, String unit, String unitPrice, String description) {
        this.productId = productId;
        this.category = category;
        this.unit = unit;
        this.unitPrice = unitPrice;
        this.description = description;
        this.createdOn = LocalDateTime.now();
        this.updatedOn = LocalDateTime.now();
    }

    public String getProductId() {
        return productId;
    }

    public String getCategory() {
        return category;
    }

    public String getUnit() {
        return unit;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productId, product.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Product.class.getSimpleName() + "[", "]")
                .add("productId='" + productId + "'")
                .add("category='" + category + "'")
                .add("unit='" + unit + "'")
                .add("unitPrice='" + unitPrice + "'")
                .add("description='" + description + "'")
                .add("createdOn=" + createdOn)
                .add("updatedOn=" + updatedOn)
                .toString();
    }
}
