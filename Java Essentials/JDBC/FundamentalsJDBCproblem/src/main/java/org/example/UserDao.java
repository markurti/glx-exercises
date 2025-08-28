package org.example;

import java.sql.*;

public class UserDao {
    private int rowsAffected = 0;

    // Get the connection
    private Connection getConnection() throws SQLException {

        String username = "postgres";
        String password = "password";
        String url = "jdbc:postgresql://localhost:5432/Fundamentals";
        return DriverManager.getConnection(url, username, password);
    }

    public void insert(User user) throws SQLException {
        String insertQuery = "INSERT INTO Users (id, name, email) VALUES (?, ?, ?)";

        // Try with resources
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(insertQuery)) {

            // Set parameters
            stmt.setInt(1, user.getId());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getEmail());

            // Execute insert query
            rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Successfully added new user to database.");
            }
        }
    }

    public static void update(User user) throws SQLException {

    }

    public static void delete(int id) throws SQLException {

    }

    public User[] getAll() throws SQLException {
        return null;
    }
}
