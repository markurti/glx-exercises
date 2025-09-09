package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDatabaseConnection extends DatabaseConnection {
    private static final String TEST_DB_URL = "jdbc:postgresql://localhost:5432/TestCustomerDatabase";
    private static final String TEST_DB_USERNAME = "postgres";
    private static final String TEST_DB_PASSWORD = "password";

    public TestDatabaseConnection() {
        super(TEST_DB_URL, TEST_DB_USERNAME, TEST_DB_PASSWORD);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(TEST_DB_URL, TEST_DB_USERNAME, TEST_DB_PASSWORD);
    }

    public void clearTable() throws SQLException {
        String clearTableSQL = "DELETE FROM Customer";

        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(clearTableSQL);
        }
    }
}
