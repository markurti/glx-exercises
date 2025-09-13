package org.example;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AppLogger {
    private static final Logger logger = Logger.getLogger(String.valueOf(AppLogger.class));
    private final String logLevel;

    public AppLogger(String logLevel) {
        this.logLevel = logLevel != null ? logLevel : "INFO";
    }

    public void logMessage(String message) {
        Level level = convertLevel(logLevel);
        logger.log(level, message);
    }

    public Level convertLevel(String javaLevel) {
        return switch (javaLevel.toUpperCase()) {
            case "SEVERE" -> Level.SEVERE;
            case "WARNING" -> Level.WARNING;
            case "INFO" -> Level.INFO;
            case "CONFIG" -> Level.CONFIG;
            case "FINE" -> Level.FINE;
            case "FINER" -> Level.FINER;
            case "FINEST" -> Level.FINEST;
            default -> Level.INFO;
        };
    }
}
