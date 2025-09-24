package com.glologistics.repository;

import com.glologistics.model.Customer;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@Log4j2
public class CustomerRepository {
    private final Map<Integer, Customer> customers = new HashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public CustomerRepository() {
        initializeSampleCustomers();
    }

    private void initializeSampleCustomers() {
        addCustomer(new Customer(0, "John Doe", 1234567890L, "123 Main St"));
        addCustomer(new Customer(0, "Jane Smith", 9876543210L, "456 Oak Ave"));
        log.info("Sample customers initialized");
    }

    public Customer addCustomer(Customer customer) {
        customer.setCustId(idCounter.getAndIncrement());
        customers.put(customer.getCustId(), customer);
        log.info("Customer added: {}", customer);
        return customer;
    }

    public Customer getCustomerById(int customerId) {
        return customers.get(customerId);
    }

    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers.values());
    }
}
