package com.glologistics.service;

import com.glologistics.model.Product;
import com.glologistics.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service class for Product operations
 * Handles business logic related to product management
 */
@Service
@Log4j2
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Add a new product to the system
     * @param product Product to be added
     * @return Added product with generated ID
     */
    public Product addProduct(Product product) {
        log.info("Adding new product: {}", product.getProductName());
        Product savedProduct = productRepository.save(product);
        log.info("Product added successfully with ID: {}", savedProduct.getProductId());
        return savedProduct;
    }

    /**
     * Update existing product details
     * @param productId ID of the product to update
     * @param quantity New quantity (if provided)
     * @param price New price (if provided)
     * @return Updated product or null if not found
     */
    public Product updateProduct(Integer productId, Integer quantity, BigDecimal price) {
        log.info("Updating product with ID: {}", productId);

        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();

            if (quantity != null && quantity >= 0) {
                product.setProductQuantityInStock(quantity);
            }
            if (price != null && price.compareTo(BigDecimal.ZERO) > 0) {
                product.setProductPrice(price);
            }

            Product updatedProduct = productRepository.save(product);
            log.info("Product updated successfully: {}", updatedProduct);
            return updatedProduct;
        }

        log.warn("Product not found with ID: {}", productId);
        return null;
    }

    /**
     * Delete a product by ID
     * @param productId ID of the product to delete
     * @return true if deleted successfully, false otherwise
     */
    public boolean deleteProduct(Integer productId) {
        log.info("Attempting to delete product with ID: {}", productId);

        if (productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
            log.info("Product deleted successfully with ID: {}", productId);
            return true;
        }

        log.warn("Product not found for deletion with ID: {}", productId);
        return false;
    }

    /**
     * Get all products
     * @return List of all products
     */
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        log.debug("Retrieving all products");
        return productRepository.findAll();
    }

    /**
     * Get product by ID
     * @param productId Product ID
     * @return Product if found, null otherwise
     */
    @Transactional(readOnly = true)
    public Product getProductById(Integer productId) {
        log.debug("Retrieving product with ID: {}", productId);
        return productRepository.findById(productId).orElse(null);
    }

    /**
     * Check if product has sufficient stock
     * @param productId Product ID
     * @param quantity Required quantity
     * @return true if sufficient stock available
     */
    @Transactional(readOnly = true)
    public boolean isProductAvailable(Integer productId, Long quantity) {
        log.debug("Checking stock availability for product ID: {} with quantity: {}",
                productId, quantity);
        return productRepository.hasProductSufficientStock(productId, quantity);
    }

    /**
     * Update product stock after order approval
     * @param productId Product ID
     * @param quantity Quantity to deduct
     */
    public void updateStock(Integer productId, Long quantity) {
        log.info("Updating stock for product ID: {} by quantity: {}", productId, quantity);

        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            int newStock = product.getProductQuantityInStock() - quantity.intValue();
            product.setProductQuantityInStock(Math.max(0, newStock));
            productRepository.save(product);

            log.info("Stock updated successfully for product ID: {}. New stock: {}",
                    productId, product.getProductQuantityInStock());
        }
    }
}