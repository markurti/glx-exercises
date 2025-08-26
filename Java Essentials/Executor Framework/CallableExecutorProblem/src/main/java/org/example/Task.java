package org.example;

import java.util.concurrent.Callable;

public class Task implements Callable<Integer> {
    private final int max;

    public Task(int max) {
        this.max = max;
    }

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 1; i <= max; i++) {
            sum += i;
        }
        return sum;
    }
}
