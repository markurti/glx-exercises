package org.example;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCustomerRepository implements CustomerRepository {
    private final Map<Integer, Customer> customerDatabase;

    public InMemoryCustomerRepository() {
        this.customerDatabase = new ConcurrentHashMap<>();
    }

    @Override
    public void addCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (customerDatabase.containsKey(customer.getCustId())) {
            throw new IllegalArgumentException("Customer with ID " + customer.getCustId() + " already exists");
        }
        customerDatabase.put(customer.getCustId(), customer);
    }

    @Override
    public Customer getCustomerById(int custId) {
        return customerDatabase.get(custId);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customerDatabase.values());
    }

    @Override
    public void updateCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (!customerDatabase.containsKey(customer.getCustId())) {
            throw new IllegalArgumentException("Customer with ID " + customer.getCustId() + " does not exist");
        }
        customerDatabase.put(customer.getCustId(), customer);
    }

    @Override
    public boolean deleteCustomer(int custId) {
        return customerDatabase.remove(custId) != null;
    }
}
