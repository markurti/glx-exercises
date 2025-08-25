package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create an ArrayList of integers
        List<Integer> numbers = new ArrayList<>();

        // Add 8 elements to the ArrayList
        for (int i = 0; i < 8; i++) {
            numbers.add((int)(Math.random() * 101)); // add a random number 0-100
        }

        // Print all elements of the ArrayList
        System.out.println("Numbers: " + numbers);
    }
}