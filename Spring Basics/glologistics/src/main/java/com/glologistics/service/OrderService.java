package com.glologistics.service;

import com.glologistics.model.Order;
import com.glologistics.model.OrderStatus;
import com.glologistics.model.Product;
import com.glologistics.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.log4j.Log4j2;
import java.time.LocalDate;
import java.util.List;

@Service
@Log4j2
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    public Order generateOrder(int customerId, int productId, long quantity) {
        Product product = productService.getProductById(productId);

        if (product == null) {
            log.error("Product not found with ID: {}", productId);
            return null;
        }

        if (!productService.isProductAvailable(productId, quantity)) {
            log.warn("Insufficient stock for product ID: {}", productId);
            return null;
        }

        Order order = new Order();
        order.setCustId(customerId);
        order.setProductId(productId);
        order.setOrderProductQuantity(quantity);
        order.setOrderTotalAmount(product.getProductPrice() * quantity);
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus(OrderStatus.PENDING);

        return orderRepository.generateOrder(order);
    }

    public boolean deleteOrder(int orderId, int customerId) {
        Order order = orderRepository.getOrderById(orderId);
        if (order != null && order.getCustId() == customerId
                && order.getOrderStatus() == OrderStatus.PENDING) {
            orderRepository.deleteOrder(orderId);
            log.info("Order {} deleted by customer {}", orderId, customerId);
            return true;
        }
        log.warn("Cannot delete order {} - either not found, not owned by customer, or already processed", orderId);
        return false;
    }

    public Order approveOrder(int orderId) {
        Order order = orderRepository.getOrderById(orderId);
        if (order != null && order.getOrderStatus() == OrderStatus.PENDING) {
            if (productService.isProductAvailable(order.getProductId(), order.getOrderProductQuantity())) {
                order.setOrderStatus(OrderStatus.APPROVED);
                productService.updateStock(order.getProductId(), order.getOrderProductQuantity());
                return orderRepository.updateOrder(order);
            }
        }
        return null;
    }

    public Order rejectOrder(int orderId) {
        Order order = orderRepository.getOrderById(orderId);
        if (order != null && order.getOrderStatus() == OrderStatus.PENDING) {
            order.setOrderStatus(OrderStatus.REJECTED);
            return orderRepository.updateOrder(order);
        }
        return null;
    }

    public List<Order> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public List<Order> getPendingOrders() {
        return orderRepository.getPendingOrders();
    }
}