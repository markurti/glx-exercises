package com.glologistics.service;

import com.glologistics.model.*;
import com.glologistics.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service class for Order operations
 * Handles business logic related to order management
 */
@Service
@Log4j2
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    /**
     * Generate a new order
     * @param customerId Customer ID
     * @param productId Product ID
     * @param quantity Quantity to order
     * @return Generated order or null if failed
     */
    public Order generateOrder(Integer customerId, Integer productId, Long quantity) {
        log.info("Generating order for customer ID: {}, product ID: {}, quantity: {}",
                customerId, productId, quantity);

        // Validate customer
        Customer customer = customerService.getCustomerById(customerId);
        if (customer == null) {
            log.error("Customer not found with ID: {}", customerId);
            return null;
        }

        // Validate product
        Product product = productService.getProductById(productId);
        if (product == null) {
            log.error("Product not found with ID: {}", productId);
            return null;
        }

        // Check stock availability
        if (!productService.isProductAvailable(productId, quantity)) {
            log.warn("Insufficient stock for product ID: {}", productId);
            return null;
        }

        // Create order
        Order order = new Order();
        order.setCustomer(customer);
        order.setProduct(product);
        order.setOrderProductQuantity(quantity);
        order.setOrderTotalAmount(product.getProductPrice().multiply(BigDecimal.valueOf(quantity)));
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus(OrderStatus.PENDING);

        Order savedOrder = orderRepository.save(order);
        log.info("Order generated successfully with ID: {}", savedOrder.getOrderId());
        return savedOrder;
    }

    /**
     * Delete an order (only PENDING orders can be deleted by customers)
     * @param orderId Order ID
     * @param customerId Customer ID (for authorization)
     * @return true if deleted successfully
     */
    public boolean deleteOrder(Integer orderId, Integer customerId) {
        log.info("Attempting to delete order ID: {} by customer ID: {}", orderId, customerId);

        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();

            if (order.getCustomer().getCustId().equals(customerId) &&
                    order.getOrderStatus() == OrderStatus.PENDING) {

                orderRepository.delete(order);
                log.info("Order {} deleted successfully by customer {}", orderId, customerId);
                return true;
            }
        }

        log.warn("Cannot delete order {} - either not found, not owned by customer, or already processed",
                orderId);
        return false;
    }

    /**
     * Approve an order (admin function)
     * @param orderId Order ID
     * @return Updated order or null if failed
     */
    public Order approveOrder(Integer orderId) {
        log.info("Approving order ID: {}", orderId);

        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();

            if (order.getOrderStatus() == OrderStatus.PENDING) {
                // Double-check stock availability
                if (productService.isProductAvailable(order.getProduct().getProductId(),
                        order.getOrderProductQuantity())) {
                    order.setOrderStatus(OrderStatus.APPROVED);
                    productService.updateStock(order.getProduct().getProductId(),
                            order.getOrderProductQuantity());

                    Order updatedOrder = orderRepository.save(order);
                    log.info("Order approved successfully: {}", orderId);
                    return updatedOrder;
                } else {
                    log.warn("Insufficient stock to approve order: {}", orderId);
                }
            }
        }

        log.error("Failed to approve order: {}", orderId);
        return null;
    }

    /**
     * Reject an order (admin function)
     * @param orderId Order ID
     * @return Updated order or null if failed
     */
    public Order rejectOrder(Integer orderId) {
        log.info("Rejecting order ID: {}", orderId);

        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();

            if (order.getOrderStatus() == OrderStatus.PENDING) {
                order.setOrderStatus(OrderStatus.REJECTED);
                Order updatedOrder = orderRepository.save(order);
                log.info("Order rejected successfully: {}", orderId);
                return updatedOrder;
            }
        }

        log.error("Failed to reject order: {}", orderId);
        return null;
    }

    /**
     * Get all orders (admin function)
     * @return List of all orders
     */
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        log.debug("Retrieving all orders");
        return orderRepository.findAllWithDetails();
    }

    /**
     * Get all pending orders
     * @return List of pending orders
     */
    @Transactional(readOnly = true)
    public List<Order> getPendingOrders() {
        log.debug("Retrieving pending orders");
        return orderRepository.findByOrderStatusWithDetails(OrderStatus.PENDING);
    }

    /**
     * Update order status (admin function)
     * @param orderId Order ID
     * @param newStatus New status
     * @return Updated order or null if failed
     */
    public Order updateOrderStatus(Integer orderId, OrderStatus newStatus) {
        log.info("Updating order ID: {} to status: {}", orderId, newStatus);

        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setOrderStatus(newStatus);
            Order updatedOrder = orderRepository.save(order);
            log.info("Order status updated successfully");
            return updatedOrder;
        }

        log.error("Order not found for status update: {}", orderId);
        return null;
    }
}