package org.example;

public class Counter implements Runnable {
    private int count;

    public Counter() {
        count = 0;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            increment();
        }
    }

    // Thread safe method
    public synchronized void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}
