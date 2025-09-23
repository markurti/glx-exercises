package com.logistics.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection implements IDatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/GloLogisticsDB";
    private static final String USER = "postgres";

    private static final String PASSWORD = "password";
    private Connection connection;

    @Override
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    @Override
    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
