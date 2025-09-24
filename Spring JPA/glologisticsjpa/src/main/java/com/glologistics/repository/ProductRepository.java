package com.glologistics.repository;

import com.glologistics.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA Repository interface for Product entity
 * Provides CRUD operations and custom query methods
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    /**
     * Find products by name (case-insensitive)
     */
    List<Product> findByProductNameContainingIgnoreCase(String productName);

    /**
     * Find products with stock greater than specified quantity
     */
    @Query("SELECT p FROM Product p WHERE p.productQuantityInStock >= :quantity")
    List<Product> findProductsWithSufficientStock(@Param("quantity") Integer quantity);

    /**
     * Check if product has sufficient stock
     */
    @Query("SELECT CASE WHEN p.productQuantityInStock >= :quantity THEN true ELSE false END " +
            "FROM Product p WHERE p.productId = :productId")
    boolean hasProductSufficientStock(@Param("productId") Integer productId,
                                      @Param("quantity") Long quantity);
}