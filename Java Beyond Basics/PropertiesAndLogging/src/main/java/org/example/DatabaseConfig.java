package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {
    private Properties properties = new Properties();

    public DatabaseConfig() {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            properties.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties: " + e.getMessage());
        }
    }

    public String getDbUrl() {
        return properties.getProperty("db.url");
    }

    public String getDbUsername() {
        return properties.getProperty("db.username");
    }

    public String getDbPassword() {
        return properties.getProperty("db.password");
    }

    public String getLogLevel() {
        return properties.getProperty("log.level", "INFO");
    }
}
