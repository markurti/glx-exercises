package com.glologistics.repository;

import com.glologistics.model.Order;
import com.glologistics.model.OrderStatus;
import org.springframework.stereotype.Repository;
import lombok.extern.log4j.Log4j2;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
@Log4j2
public class OrderRepository {
    private final Map<Integer, Order> orders = new HashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(1000);

    public Order generateOrder(Order order) {
        order.setOrderId(idCounter.getAndIncrement());
        orders.put(order.getOrderId(), order);
        log.info("Order generated: {}", order);
        return order;
    }

    public Order updateOrder(Order order) {
        orders.put(order.getOrderId(), order);
        log.info("Order updated: {}", order);
        return order;
    }

    public void deleteOrder(int orderId) {
        orders.remove(orderId);
        log.info("Order deleted with ID: {}", orderId);
    }

    public Order getOrderById(int orderId) {
        return orders.get(orderId);
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>(orders.values());
    }

    public List<Order> getOrdersByCustomerId(int customerId) {
        return orders.values().stream()
                .filter(order -> order.getCustId() == customerId)
                .collect(Collectors.toList());
    }

    public List<Order> getPendingOrders() {
        return orders.values().stream()
                .filter(order -> order.getOrderStatus() == OrderStatus.PENDING)
                .collect(Collectors.toList());
    }
}
