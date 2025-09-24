package com.glologistics.repository;

import com.glologistics.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA Repository interface for Customer entity
 * Provides CRUD operations and custom query methods
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    /**
     * Find customer by contact number
     */
    Optional<Customer> findByCustContact(String custContact);

    /**
     * Find customers by name (case-insensitive)
     */
    List<Customer> findByCustNameContainingIgnoreCase(String custName);

    /**
     * Find customers with orders
     */
    @Query("SELECT DISTINCT c FROM Customer c JOIN c.orders o")
    List<Customer> findCustomersWithOrders();
}
