package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDao {
    private int rowsAffected = 0;

    // Get the connection
    private Statement getConnection() throws SQLException {
        String username = "postgres";
        String password = "password";
        String url = "jdbc:postgresql://localhost:5432/Fundamentals";
        Connection connection = DriverManager.getConnection(url, username, password);
        return connection.createStatement();
    }

    public static void insert(User user) throws SQLException {

    }

    public static void update(User user) throws SQLException {

    }

    public static void delete(int id) throws SQLException {

    }

    public User[] getAll() throws SQLException {
        return null;
    }
}
