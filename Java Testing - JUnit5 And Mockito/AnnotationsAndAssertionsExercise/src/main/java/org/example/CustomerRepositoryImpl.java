package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CustomerRepositoryImpl implements CustomerRepository {
    private DatabaseConnection dbConnection;

    public CustomerRepositoryImpl(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public void addCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }

        String insertSQL = "INSERT INTO Customer (custId, customerName, contactNumber, address) VALUES (?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setInt(1, customer.getCustId());
            pstmt.setString(2, customer.getCustomerName());
            pstmt.setString(3, customer.getContactNumber());
            pstmt.setString(4, customer.getAddress());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Failed to insert customer");
            }

        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) { // PostgreSQL unique violation
                throw new IllegalArgumentException("Customer with ID " + customer.getCustId() + " already exists");
            }
            throw new RuntimeException("Database error while adding customer", e);
        }
    }

    @Override
    public Customer getCustomerById(int custId) {
        String selectSQL = "SELECT custId, customerName, contactNumber, address FROM Customer WHERE custId = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {

            pstmt.setInt(1, custId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCustomer(rs);
                }
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Database error while finding customer by ID", e);
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String selectSQL = "SELECT custId, customerName, contactNumber, address FROM Customer ORDER BY custId";

        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {

            while (rs.next()) {
                customers.add(mapResultSetToCustomer(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Database error while finding all customers", e);
        }

        return customers;
    }

    @Override
    public void updateCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }

        String updateSQL = "UPDATE Customer SET customerName = ?, contactNumber = ?, address = ? WHERE custId = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {

            pstmt.setString(1, customer.getCustomerName());
            pstmt.setString(2, customer.getContactNumber());
            pstmt.setString(3, customer.getAddress());
            pstmt.setInt(4, customer.getCustId());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new IllegalArgumentException("Customer with ID " + customer.getCustId() + " does not exist");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Database error while updating customer", e);
        }
    }

    @Override
    public boolean deleteCustomer(int custId) {
        String deleteSQL = "DELETE FROM Customer WHERE custId = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {

            pstmt.setInt(1, custId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Database error while deleting customer", e);
        }
    }

    private Customer mapResultSetToCustomer(ResultSet rs) throws SQLException {
        return new Customer(
                rs.getInt("custId"),
                rs.getString("customerName"),
                rs.getString("contactNumber"),
                rs.getString("address")
        );
    }
}
