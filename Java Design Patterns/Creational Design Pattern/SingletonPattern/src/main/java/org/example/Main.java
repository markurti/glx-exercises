package org.example;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        // Multithreading demo
        Thread thread1 = new Thread(() -> {
            Logger logger = Logger.getLoggerInstance();
            System.out.println("Instance from thread 1: " + logger);
        });

        Thread thread2 = new Thread(() -> {
            Logger logger = Logger.getLoggerInstance();
            System.out.println("Instance from thread 2: " + logger);
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (Exception e) {
            System.out.println("Failed creating instance with threads: " + e.getMessage());
        }

        // Demo for serializing/deserializing problem
        serializeLogger();
        deserializeLogger();

        // Cloning problem demo
        Logger logger = Logger.getLoggerInstance();
        try {
            Logger logger2 = (Logger) logger.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("Failed cloning: " + e.getMessage());
        }
    }

    private static void serializeLogger() {
        Logger logger = Logger.getLoggerInstance();

        FileOutputStream file = null;
        ObjectOutputStream out = null;

        try {
            file = new FileOutputStream("C:\\Users\\Mark\\OneDrive\\Documents\\Year-1-CS\\manualExeclab3.txt");
            out = new ObjectOutputStream(file);

            out.writeObject(logger);

            System.out.println("Object has been serialized: " + logger);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (file != null) {
                    file.close();
                }
            } catch (IOException e) {
                System.out.println("Error while closing streams: " + e.getMessage());
            }
        }
    }

    private static void deserializeLogger() {
        FileInputStream file = null;
        ObjectInputStream in = null;
        Logger logger = null;

        try {
            file = new FileInputStream("C:\\Users\\Mark\\OneDrive\\Documents\\Year-1-CS\\manualExeclab3.txt");
            in = new ObjectInputStream(file);

            logger = (Logger) in.readObject();

            System.out.println("Object has been deserialized: " + logger);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (file != null) {
                    file.close();
                }
            } catch (IOException e) {
                System.out.println("Error while closing streams: " + e.getMessage());
            }
        }
    }
}