package com.hitachi.mobile.repository.impl;

import com.hitachi.mobile.entity.Customer;
import com.hitachi.mobile.exception.CustomerDoesNotExistException;
import com.hitachi.mobile.exception.CustomerTableEmptyException;
import com.hitachi.mobile.repository.CustomerRepository;
import com.hitachi.mobile.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerRepositoryImpl implements CustomerRepository {

    @Override
    public List<Customer> findAll() throws CustomerTableEmptyException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Customer> customers = session.createQuery("FROM Customer", Customer.class).getResultList();
            if (customers.isEmpty()) {
                throw new CustomerTableEmptyException("No data found in customer table");
            }
            return customers;
        }
    }

    @Override
    public List<Customer> findByCity(String city) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Customer", Customer.class)
                    .getResultList()
                    .stream()
                    .filter(customer -> city.equalsIgnoreCase(customer.getCity()))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Customer findById(Long uniqueIdNumber) throws CustomerDoesNotExistException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Customer customer = session.get(Customer.class, uniqueIdNumber);
            if (customer == null) {
                throw new CustomerDoesNotExistException("No customer found for given unique Id");
            }
            return customer;
        }
    }

    @Override
    public void updateAddress(Long uniqueIdNumber, String city, String state) throws CustomerDoesNotExistException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Customer customer = session.get(Customer.class, uniqueIdNumber);
            if (customer == null) {
                throw new CustomerDoesNotExistException("No customer found for given unique Id");
            }

            customer.setCity(city);
            customer.setState(state);
            session.merge(customer);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
