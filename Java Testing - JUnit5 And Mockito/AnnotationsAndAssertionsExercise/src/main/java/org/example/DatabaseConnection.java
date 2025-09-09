package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseConnection {
    private static final String url = "jdbc:postgresql://localhost:5432/CustomerDatabase";
    private static final String user = "postgres";
    private static final String password = "password";

    public DatabaseConnection() {
        this(url, user, password);
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
