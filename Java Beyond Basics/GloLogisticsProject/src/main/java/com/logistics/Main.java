package com.logistics;

import com.logistics.application.*;
import com.logistics.database.*;
import com.logistics.ui.ConsoleMenu;

public class Main {
    public static void main(String[] args) {
        System.out.println("Initializing Logistics Management System...");

        // Initialize database connection with dependency injection
        IDatabaseConnection dbConnection = new DatabaseConnection();

        // Initialize application with dependency injection
        ILogisticsService logisticsApp = new LogisticsApplication(dbConnection);

        // Initialize and run console menu
        ConsoleMenu menu = new ConsoleMenu(logisticsApp);
        menu.run();

        // Close database connection
        try {
            dbConnection.closeConnection();
        } catch (Exception e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
}