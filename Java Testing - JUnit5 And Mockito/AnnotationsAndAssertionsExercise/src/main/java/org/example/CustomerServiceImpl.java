package org.example;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void addCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (customer.getCustomerName() == null || customer.getCustomerName().trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be null or empty");
        }
        if (customer.getContactNumber() == null || customer.getContactNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Contact number cannot be null or empty");
        }

        customerRepository.addCustomer(customer);
    }

    @Override
    public Customer getCustomerById(int custId) {
        if (custId <= 0) {
            throw new IllegalArgumentException("Customer ID must be positive");
        }
        return customerRepository.getCustomerById(custId);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }

    @Override
    public void updateCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (customer.getCustomerName() == null || customer.getCustomerName().trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be null or empty");
        }
        if (customer.getContactNumber() == null || customer.getContactNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Contact number cannot be null or empty");
        }
        customerRepository.updateCustomer(customer);
    }

    @Override
    public boolean deleteCustomer(int custId) {
        if (custId <= 0) {
            throw new IllegalArgumentException("Customer ID must be positive");
        }
        return customerRepository.deleteCustomer(custId);
    }
}
