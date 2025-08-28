package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public void update(User user, int id) throws SQLException {
        String updateQuery = "UPDATE Users SET id = ?, name = ?, email = ? WHERE id = ?";

        // Try with resources
        try (Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement(updateQuery)) {
            // Set parameters
            stmt.setInt(1, user.getId());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getEmail());
            stmt.setInt(4, id);

            // Execute update query
            rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Successfully updated user.");
            }
        }
    }

    public void delete(int id) throws SQLException {
        String deleteQuery = "DELETE FROM Users WHERE id = ?";

        // Try with resources
        try (Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement(deleteQuery)) {
            // Set parameter
            stmt.setInt(1, id);

            // Execute delete query
            rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Successfully deleted user.");
            }
        }
    }

    public List<User> getAll() throws SQLException {
        String selectQuery = "SELECT * FROM Users";

        // Try with resources
        try (Connection connection = getConnection();
        Statement stmt = connection.createStatement()) {
            // Execute select query
            ResultSet rs = stmt.executeQuery(selectQuery);

            // Retrieve all user data
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                users.add(new User(id, name, email));
            }

            return users;
        }
    }
}
