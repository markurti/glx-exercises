package org.example;

public class Main {
    public static void main(String[] args) {
        // Multithreading demo
        try {
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

            thread1.join();
            thread2.join();
        } catch (Exception e) {
            System.out.println("Failed creating instance with threads: " + e.getMessage());
        }


    }
}