package org.example;

public class Counter {
    private int count;

    public Counter() {
        count = 0;
    }

    // Thread safe method
    public synchronized void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}
