package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        Task task = new Task();
        for (int i = 0; i < 20; i++) {
            executorService.execute(task);
        }

        executorService.shutdown();

        if (!executorService.awaitTermination(1, TimeUnit.MINUTES)) {
            System.out.println("Terminating tasks forcefully...");
            executorService.shutdownNow();
        }
        if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
            System.err.println("Executor did not terminate.");
        }

        System.out.println("All tasks finished.");
    }
}