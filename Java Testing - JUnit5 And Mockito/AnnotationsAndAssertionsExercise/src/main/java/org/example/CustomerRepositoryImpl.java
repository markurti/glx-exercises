package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CustomerRepositoryImpl implements CustomerRepository {
    private final Map<Integer, Customer> customerDatabase;

    public CustomerRepositoryImpl() {
        this.customerDatabase = new ConcurrentHashMap<>();
    }

    @Override
    public void addCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (customerDatabase.containsKey(customer.getCustId())) {
            throw new IllegalArgumentException("Customer with id " + customer.getCustId() + " already exists");
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
            throw new IllegalArgumentException("Customer with id " + customer.getCustId() + " does not exist");
        }
        customerDatabase.put(customer.getCustId(), customer);
    }

    @Override
    public boolean deleteCustomer(int custId) {
        return customerDatabase.remove(custId) != null;
    }
}
