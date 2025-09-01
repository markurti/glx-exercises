package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
