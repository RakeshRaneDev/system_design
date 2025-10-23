package org.byte_stroke.inventory_management_system;

import java.util.*;

public class ProductRepository {
    private final Map<String, Product> productIdToProduct = new HashMap<>();

    public Optional<Product> findById(String productId) {
        return Optional.ofNullable(productIdToProduct.get(productId));
    }

    public List<Product> findAll() {
        return new ArrayList<>(productIdToProduct.values());
    }

    public void save(Product product) {
        productIdToProduct.put(product.getProductId(), product);
    }

    public boolean delete(String productId) {
        return productIdToProduct.remove(productId) != null;
    }
}
