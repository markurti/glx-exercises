package org.example;

import java.sql.SQLException;

public class DatabaseManager {
    private final ConnectionPool connectionPool;

    public DatabaseManager(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public void performOperation(String query) throws SQLException, InterruptedException {
        DatabaseConnection connection = connectionPool.getConnection();

        try {
            connection.getConnection().prepareStatement(query).execute();
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }
}
