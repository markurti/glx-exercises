package org.example;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ConcurrentHashMap<String, Integer> storage = new ConcurrentHashMap<>();

        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 100; i++) {
            executor.execute(new Task(storage, "abc", 123));
        }

        executor.shutdown();
        if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
            System.out.println("Executor did not terminate: Beginning forceful shutdown...");
            executor.shutdownNow();
        }
        if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
            System.err.println("Executor did not terminate after forceful shutdown attempt.");
        }

        System.out.println("The final size of the ConcurrentHashMap is: " + storage.size());
    }
}