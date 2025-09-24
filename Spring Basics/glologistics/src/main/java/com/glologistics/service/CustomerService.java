package com.glologistics.service;

import com.glologistics.model.Customer;
import com.glologistics.model.Order;
import com.glologistics.model.Product;
import com.glologistics.repository.CustomerRepository;
import com.glologistics.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.log4j.Log4j2;
import java.util.List;

@Service
@Log4j2
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    public Customer addCustomer(Customer customer) {
        log.info("Adding new customer: {}", customer.getCustName());
        return customerRepository.addCustomer(customer);
    }

    public Customer getCustomerById(int customerId) {
        return customerRepository.getCustomerById(customerId);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }

    public List<Product> viewAllProducts() {
        return productService.getAllProducts();
    }

    public List<Order> viewAllOrders(int customerId) {
        return orderRepository.getOrdersByCustomerId(customerId);
    }
}
