package com.glologistics.repository;

import com.glologistics.model.Product;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@Log4j2
public class ProductRepository {
    private final Map<Integer, Product> products = new HashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public ProductRepository() {
        initializeSampleProducts();
    }

    private void initializeSampleProducts() {
        addProduct(new Product(0, "Laptop", "High-performance laptop", 999.99, 50));
        addProduct(new Product(0, "Mouse", "Wireless mouse", 25.99, 200));
        addProduct(new Product(0, "Keyboard", "Mechanical keyboard", 79.99, 150));
        addProduct(new Product(0, "Monitor", "27-inch LED monitor", 299.99, 75));
        log.info("Sample products initialized");
    }

    public Product addProduct(Product product) {
        product.setProductId(idCounter.getAndIncrement());
        products.put(product.getProductId(), product);
        log.info("Product added: {}", product);
        return product;
    }

    public Product updateProduct(Product product) {
        products.put(product.getProductId(), product);
        log.info("Product updated: {}", product);
        return product;
    }

    public void deleteProduct(int productId) {
        products.remove(productId);
        log.info("Product deleted with ID: {}", productId);
    }

    public Product getProductById(int productId) {
        return products.get(productId);
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }
}
