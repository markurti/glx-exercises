package com.glologistics.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

/**
 * Product Entity representing the product table in PostgreSQL database
 */
@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @NotNull
    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_description")
    private String productDescription;

    @NotNull
    @Positive
    @Column(name = "product_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal productPrice;

    @NotNull
    @PositiveOrZero
    @Column(name = "product_quantity_instock", nullable = false)
    private Integer productQuantityInStock;

    @Override
    public String toString() {
        return "Product [productId=" + productId + ", productName=" + productName + ", productDescription=" +
                productDescription + ", productPrice=" + productPrice + ", productQuantityInStock=" +
                productQuantityInStock + "]";
    }
}