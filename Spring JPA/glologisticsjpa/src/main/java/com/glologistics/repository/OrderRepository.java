package com.glologistics.repository;

import com.glologistics.model.Order;
import com.glologistics.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * JPA Repository interface for Order entity
 * Provides CRUD operations and custom query methods
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    /**
     * Find orders by customer ID
     */
    @Query("SELECT o FROM Order o " +
            "JOIN FETCH o.product p " +
            "JOIN FETCH o.customer c " +
            "WHERE o.customer.custId = :customerId")
    List<Order> findByCustomerId(@Param("customerId") Integer customerId);

    @Query("SELECT o FROM Order o " +
            "JOIN FETCH o.product p " +
            "JOIN FETCH o.customer c " +
            "WHERE o.orderStatus = :status")
    List<Order> findByOrderStatusWithDetails(@Param("status") OrderStatus orderStatus);

    @Query("SELECT o FROM Order o " +
            "JOIN FETCH o.product p " +
            "JOIN FETCH o.customer c")
    List<Order> findAllWithDetails();

    /**
     * Find orders by status
     */
    List<Order> findByOrderStatus(OrderStatus orderStatus);

    /**
     * Find orders by date range
     */
    @Query("SELECT o FROM Order o " +
            "JOIN FETCH o.product p " +
            "JOIN FETCH o.customer c " +
            "WHERE o.orderDate BETWEEN :startDate AND :endDate")
    List<Order> findByOrderDateBetween(@Param("startDate") LocalDate startDate,
                                                  @Param("endDate") LocalDate endDate);

    /**
     * Find orders by product ID
     */
    @Query("SELECT o FROM Order o " +
            "JOIN FETCH o.product p " +
            "JOIN FETCH o.customer c " +
            "WHERE o.product.productId = :productId")
    List<Order> findByProductId(@Param("productId") Integer productId);

    /**
     * Find pending orders for a specific customer
     */
    @Query("SELECT o FROM Order o " +
            "JOIN FETCH o.product p " +
            "JOIN FETCH o.customer c " +
            "WHERE o.customer.custId = :customerId AND o.orderStatus = 'PENDING'")
    List<Order> findPendingOrdersByCustomerId(@Param("customerId") Integer customerId);
}
