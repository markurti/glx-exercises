package com.glologistics.utility;

import com.glologistics.model.Customer;
import com.glologistics.model.Product;
import com.glologistics.service.CustomerService;
import com.glologistics.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Implements ApplicationRunner to initialize data after application context is ready
 */
@Component
@Log4j2
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initializeDefaultData();
    }

    /**
     * Initialize default data if database is empty
     */
    private void initializeDefaultData() {
        try {
            // Initialize products if none exist
            if (productService.getAllProducts().isEmpty()) {
                log.info("Database is empty. Initializing default products...");

                productService.addProduct(new Product(null, "Gaming Laptop",
                        "High-performance gaming laptop with RTX graphics",
                        new BigDecimal("1299.99"), 25));

                productService.addProduct(new Product(null, "Wireless Mouse",
                        "Ergonomic wireless mouse with RGB lighting",
                        new BigDecimal("35.99"), 150));

                productService.addProduct(new Product(null, "Mechanical Keyboard",
                        "RGB mechanical keyboard with blue switches",
                        new BigDecimal("89.99"), 75));

                productService.addProduct(new Product(null, "4K Monitor",
                        "32-inch 4K UHD monitor with HDR support",
                        new BigDecimal("449.99"), 40));

                productService.addProduct(new Product(null, "Webcam",
                        "1080p HD webcam with noise cancellation",
                        new BigDecimal("79.99"), 100));

                log.info("Default products initialized successfully");
            }

            // Initialize customers if none exist
            if (customerService.getAllCustomers().isEmpty()) {
                log.info("Initializing default customers...");

                customerService.addCustomer(new Customer(null, "Alice Johnson",
                        "555-0101", "789 Pine St, City, State"));

                customerService.addCustomer(new Customer(null, "Bob Wilson",
                        "555-0102", "321 Oak Ave, City, State"));

                customerService.addCustomer(new Customer(null, "Carol Davis",
                        "555-0103", "654 Maple Dr, City, State"));

                log.info("Default customers initialized successfully");
            }

        } catch (Exception e) {
            log.error("Error initializing default data: {}", e.getMessage(), e);
        }
    }
}
