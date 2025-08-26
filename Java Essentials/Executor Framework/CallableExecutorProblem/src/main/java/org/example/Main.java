package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        List<Future<Integer>> futures = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 10; i++) {
            Future<Integer> result = executor.submit(new Task(i + 1));
            futures.add(result);
        }

        executor.shutdown();

        if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
            System.out.println("Forcefully terminating tasks...");
            executor.shutdownNow();
        }
        if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
            System.err.println("Executor did not shut down.");
        }
        System.out.println("All tasks completed.");

        for (int i = 0; i < futures.size(); i++) {
            try {
                System.out.println("Task" + (i + 1) + "'s result: " + futures.get(i).get());
            } catch (NullPointerException e) {
                System.out.println("Task" + (i + 1) + "'s result doesn't exist (null).");
            }
        }
    }
}