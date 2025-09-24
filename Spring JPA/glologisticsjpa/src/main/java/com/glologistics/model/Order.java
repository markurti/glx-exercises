package com.glologistics.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Order Entity representing the orders table in PostgreSQL database
 */
@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @NotNull
    @Positive
    @Column(name = "order_total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal orderTotalAmount;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    @NotNull
    @Positive
    @Column(name = "order_product_quantity", nullable = false)
    private Long orderProductQuantity;

    // Many orders can reference one product
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // Many orders can belong to one customer
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cust_id", nullable = false)
    private Customer customer;

    @Override
    public String toString() {
        return "Order [orderId=" + orderId + ", orderTotalAmount=" + orderTotalAmount +
                ", orderDate=" + orderDate + ", orderStatus=" + orderStatus + ", orderProductQuantity=" +
                orderProductQuantity + ", product=" + product + ", customer=" + customer + "]";
    }
}