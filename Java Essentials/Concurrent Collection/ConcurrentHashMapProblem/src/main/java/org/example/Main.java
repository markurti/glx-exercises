package org.example;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        ConcurrentHashMap<String, Integer> storage = new ConcurrentHashMap<>();

        ExecutorService executor = Executors.newFixedThreadPool(5);
    }
}