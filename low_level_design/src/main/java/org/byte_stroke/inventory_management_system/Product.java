package org.byte_stroke.inventory_management_system;

import java.util.Objects;

public class Product {
    private final String productId; // immutable identifier
    private String name;
    private String description;

    public Product(String productId, String name, String description) {
        if (productId == null || productId.isBlank()) {
            throw new IllegalArgumentException("productId must be non-empty");
        }
        this.productId = productId;
        this.name = name;
        this.description = description;
    }

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productId.equals(product.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
