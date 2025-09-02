package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private final Connection connection;

    public DatabaseConnection(String url, String username, String password) throws SQLException {
        this.connection = DriverManager.getConnection(url, username, password);
    }
}
