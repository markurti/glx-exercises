package org.example;

import java.util.concurrent.ConcurrentHashMap;

public class Task implements Runnable {
    private ConcurrentHashMap<String, Integer> map;
    private String key;
    private int value;

    public Task(ConcurrentHashMap<String, Integer> map, String key, int value) {
        this.map = map;
        this.key = key;
        this.value = value;
    }

    @Override
    public void run() {
        map.put(key, value);
    }
}
