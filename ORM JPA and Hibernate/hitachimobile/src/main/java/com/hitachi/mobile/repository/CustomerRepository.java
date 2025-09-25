package com.hitachi.mobile.repository;

import com.hitachi.mobile.entity.Customer;
import com.hitachi.mobile.exception.CustomerDoesNotExistException;
import com.hitachi.mobile.exception.CustomerTableEmptyException;
import java.util.List;

public interface CustomerRepository {
    List<Customer> findAll() throws CustomerTableEmptyException;
    List<Customer> findByCity(String city);
    Customer findById(Long uniqueIdNumber) throws CustomerDoesNotExistException;
    void updateAddress(Long uniqueIdNumber, String city, String state) throws CustomerDoesNotExistException;
}
