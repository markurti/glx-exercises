package com.hitachi.mobile.service;

import com.hitachi.mobile.entity.Customer;
import com.hitachi.mobile.exception.CustomerDoesNotExistException;
import com.hitachi.mobile.exception.CustomerTableEmptyException;
import com.hitachi.mobile.repository.CustomerRepository;
import com.hitachi.mobile.repository.impl.CustomerRepositoryImpl;

import java.util.List;

public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService() {
        this.customerRepository = new CustomerRepositoryImpl();
    }

    public List<Customer> getCustomersInBangalore() {
        return customerRepository.findByCity("Bangalore");
    }

    public List<Customer> getAllCustomers() throws CustomerTableEmptyException {
        return customerRepository.findAll();
    }

    public void updateCustomerAddress(Long uniqueIdNumber, String city, String state)
            throws CustomerDoesNotExistException {
        customerRepository.updateAddress(uniqueIdNumber, city, state);
    }
}
