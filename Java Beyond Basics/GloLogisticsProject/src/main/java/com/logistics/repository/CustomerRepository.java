package com.logistics.repository;

import com.logistics.models.Customer;
import com.logistics.models.Contact;
import com.logistics.database.IDatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository implements IRepository<Customer> {
    private final IDatabaseConnection dbConnection;

    public CustomerRepository(IDatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public Customer save(Customer customer) {
        String sql = "INSERT INTO customers (name, contact_name, contact_email, contact_phone) " +
                "VALUES (?,?,?,?) RETURNING id";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getContactInfo().getName());
            stmt.setString(3, customer.getContactInfo().getEmail());
            stmt.setString(4, customer.getContactInfo().getPhone());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                customer.setId(rs.getLong("id"));
            }
        } catch (SQLException e) {
            System.out.println("Error saving customer: " + e.getMessage());
        }
        return customer;
    }

    @Override
    public Customer findById(Long id) {
        String sql = "SELECT * FROM customers WHERE id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToCustomer(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error finding customer with id " + id + ": " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";

        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                customers.add(mapResultSetToCustomer(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching customers: " + e.getMessage());
        }
        return customers;
    }

    @Override
    public void update(Customer customer) {
        String sql = "UPDATE customers SET name = ?, contact_name = ?, contact_email = ?, " +
                "contact_phone = ? WHERE id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getContactInfo().getName());
            stmt.setString(3, customer.getContactInfo().getEmail());
            stmt.setString(4, customer.getContactInfo().getPhone());
            stmt.setLong(5, customer.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating customer: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM customers WHERE id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting customer: " + e.getMessage());
        }
    }

    private Customer mapResultSetToCustomer(ResultSet rs) throws SQLException {
        Contact contact = new Contact(
                rs.getString("contact_name"),
                rs.getString("contact_email"),
                rs.getString("contact_phone")
        );

        Customer customer = new Customer(rs.getString("name"), contact);
        customer.setId(rs.getLong("id"));
        return customer;
    }
}
