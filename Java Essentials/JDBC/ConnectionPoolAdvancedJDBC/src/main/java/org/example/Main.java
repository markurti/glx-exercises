package org.example;

import java.lang.reflect.Modifier;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
         ConnectionPool connectionPool = new ConnectionPool(
                 "jdbc:postgresql://localhost:5432/Fundamentals",
                 "postgres",
                 "password",
                 5);

         DatabaseManager dbManager = new DatabaseManager(connectionPool);

         Thread[] threads = new Thread[10];

         for (int i = 0; i < threads.length; i++) {
             final int threadId = i + 1;
             threads[i] = new Thread(() -> {
                try {
                    System.out.println("Thread " + threadId + " started processing tasks...");
                    dbManager.performOperation("SELECT * FROM Product");
                    Thread.sleep(100);
                    dbManager.performOperation("SELECT * FROM Product");
                } catch (InterruptedException | SQLException e) {
                    System.err.println("Thread " + threadId + " failed: " + e.getMessage());
                }
             });
         }

         // Start the threads
        for (Thread thread : threads) {
            thread.start();
        }

        // Wait for all threads to finish
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println("Thread failed: " + e.getMessage());
            }
        }

        System.out.println("All threads finished.");
    }
}