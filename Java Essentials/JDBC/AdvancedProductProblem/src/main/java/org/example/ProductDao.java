package org.example;

import java.sql.*;

public class ProductDao {
    private int rowsAffected = 0;

    // Get the connection
    private Connection getConnection() throws SQLException {
        String username = "postgres";
        String password = "password";
        String url = "jdbc:postgresql://localhost:5432/Fundamentals";
        return DriverManager.getConnection(url, username, password);
    }

    public void insert(Product product) throws SQLException {
        String insertQuery = "INSERT INTO Product (name, stock) VALUES (?, ?)";

        try (Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(insertQuery)) {

            // Set parameters
            ps.setString(1, product.getName());
            ps.setInt(2, product.getStock());

            // Execute insert query
            rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Successfully inserted new product in database.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addStock(int id, int amount) throws SQLException {
        String addStockQuery = "UPDATE Product SET stock = stock + ? WHERE id = ?";

        try (Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(addStockQuery)) {

            // Set parameters
            ps.setInt(1, amount);
            ps.setInt(2, id);

            // Execute query
            rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Successfully updated stock in product.");
            } else {
                System.out.println("Failed to update stock in product.");
            }
        }
    }

    public void reduceStock(int id, int amount) throws SQLException {
        String checkStockQuery = "SELECT stock FROM Product WHERE id = ?";
        String reduceStockQuery = "UPDATE Product SET stock = stock - ? WHERE id = ?";

        try (Connection conn = getConnection()) {
            try {
                // Disable auto-commit for atomicity
                conn.setAutoCommit(false);

                // Set isolation level to Read Committed to prevent dirty reads
                conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

                try (PreparedStatement ps = conn.prepareStatement(checkStockQuery)) {
                    ps.setInt(1, id);

                    ResultSet rs = ps.executeQuery();

                    if (!rs.next()) {
                        throw new SQLException("Product with id " + id + " does not exist.");
                    }

                    int currentStock = rs.getInt("stock");

                    if (currentStock < amount) {
                        throw new SQLException("Insufficient stock. Available stock: " + currentStock);
                    }
                }

                try (PreparedStatement ps = conn.prepareStatement(reduceStockQuery)) {
                    ps.setInt(1, amount);
                    ps.setInt(2, id);

                    rowsAffected = ps.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Successfully reduced " + amount + " of stock in the product.");
                    } else {
                        throw new SQLException("Failed to update product stock.");
                    }
                }

                conn.commit();
            } catch (SQLException e) {
                System.out.println(e.getMessage());

                // Rollback on any error
                conn.rollback();
            } finally {
                // Restore auto-commit
                conn.setAutoCommit(true);
            }
        }
    }
}
