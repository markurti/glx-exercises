package org.example;

import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(String.valueOf(Main.class));

    public static void main(String[] args) {
        logger.info("Application starting...");

        DatabaseConfig config = new DatabaseConfig();

        logger.info("Database URL: " + config.getDbUrl());
        logger.info("Database Username: " + config.getDbUsername());
        logger.info("Log Level: " + config.getLogLevel());

        AppLogger appLogger = new AppLogger(config.getLogLevel());
        appLogger.logMessage("This message is logged at the configured level.");

        logger.info("Application finished");
    }
}