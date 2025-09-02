package org.example;

import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionPool {
    private final BlockingQueue<DatabaseConnection> connectionPool;

    public ConnectionPool(String url, String username, String password, int maxPoolSize) {
        this.connectionPool = new LinkedBlockingQueue<>();

        // Initialize pool with connections
        for (int i = 0; i < maxPoolSize; i++) {
            try {
                DatabaseConnection connection = new DatabaseConnection(url, username, password);
                connectionPool.add(connection);
            } catch (SQLException e) {
                throw new RuntimeException("Failed to initialize connection pool:" + e);
            }
        }
    }

    public DatabaseConnection getConnection() throws InterruptedException {
        return connectionPool.take();
    }

    public void releaseConnection(DatabaseConnection connection) {
        if (connection != null) {
                connectionPool.add(connection);
        }
    }
}
