package org.example;

import java.util.List;

public interface CustomerRepository {
    void addCustomer(Customer customer);
    Customer getCustomerById(int custId);
    List<Customer> getAllCustomers();
    void updateCustomer(Customer customer);
    boolean deleteCustomer(int custId);
}
