package com.glologistics.service;

import com.glologistics.model.Customer;
import com.glologistics.model.Order;
import com.glologistics.model.Product;
import com.glologistics.repository.CustomerRepository;
import com.glologistics.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class for Customer operations
 * Handles business logic related to customer management
 */
@Service
@Log4j2
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    /**
     * Add a new customer to the system with validation
     * @param customer Customer to be added
     * @return Added customer with generated ID
     * @throws IllegalArgumentException if validation fails
     */
    public Customer addCustomer(Customer customer) {
        log.info("Adding new customer: {}", customer.getCustName());

        // Validate customer input according to entity constraints
        validateCustomer(customer);

        // Check if customer with same contact already exists
        if (customerRepository.findByCustContact(customer.getCustContact()).isPresent()) {
            log.warn("Customer with contact {} already exists", customer.getCustContact());
            throw new IllegalArgumentException("Customer with this contact number already exists");
        }

        Customer savedCustomer = customerRepository.save(customer);
        log.info("Customer added successfully with ID: {}", savedCustomer.getCustId());
        return savedCustomer;
    }

    /**
     * Validate customer data according to entity constraints
     * @param customer Customer to validate
     * @throws IllegalArgumentException if validation fails
     */
    private void validateCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }

        // Validate customer name
        if (customer.getCustName() == null || customer.getCustName().trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name is required and cannot be empty");
        }

        // Validate customer name length
        if (customer.getCustName().trim().length() > 100) {
            throw new IllegalArgumentException("Customer name cannot exceed 100 characters");
        }

        // Validate customer contact
        if (customer.getCustContact() == null || customer.getCustContact().trim().isEmpty()) {
            throw new IllegalArgumentException("Customer contact is required and cannot be empty");
        }

        // Validate contact format
        String contact = customer.getCustContact().trim();
        if (!contact.matches("^[0-9+\\-\\s\\(\\)]{10,15}$")) {
            throw new IllegalArgumentException("Customer contact must be a valid phone number (10-15 digits)");
        }

        // Validate customer address
        if (customer.getCustAdd() == null || customer.getCustAdd().trim().isEmpty()) {
            throw new IllegalArgumentException("Customer address is required and cannot be empty");
        }

        // Validate address length
        if (customer.getCustAdd().trim().length() > 255) {
            throw new IllegalArgumentException("Customer address cannot exceed 255 characters");
        }

        // Sanitize input data
        customer.setCustName(customer.getCustName().trim());
        customer.setCustContact(customer.getCustContact().trim());
        customer.setCustAdd(customer.getCustAdd().trim());

        log.debug("Customer validation passed for: {}", customer.getCustName());
    }


    /**
     * Get customer by ID
     * @param customerId Customer ID
     * @return Customer if found, null otherwise
     */
    @Transactional(readOnly = true)
    public Customer getCustomerById(Integer customerId) {
        log.debug("Retrieving customer with ID: {}", customerId);
        return customerRepository.findById(customerId).orElse(null);
    }

    /**
     * Get all customers
     * @return List of all customers
     */
    @Transactional(readOnly = true)
    public List<Customer> getAllCustomers() {
        log.debug("Retrieving all customers");
        return customerRepository.findAll();
    }

    /**
     * View all products (delegated to ProductService)
     * @return List of all products
     */
    @Transactional(readOnly = true)
    public List<Product> viewAllProducts() {
        return productService.getAllProducts();
    }

    /**
     * View all orders for a specific customer
     * @param customerId Customer ID
     * @return List of customer's orders
     */
    @Transactional(readOnly = true)
    public List<Order> viewAllOrders(Integer customerId) {
        log.debug("Retrieving orders for customer ID: {}", customerId);
        return orderRepository.findByCustomerId(customerId);
    }
}
