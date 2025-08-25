package org.example;

public class Main {
    public static void main(String[] args) {
        // Create String, Integer pair
        Pair<String, Integer> pair1 = new Pair<>("abc", 123);
        Pair<Boolean, Integer> pair2 = new Pair<>(true, 456);

        // Test swap and print methods
        Pair.printPair(pair2);
        Pair<Integer, Boolean> swappedPair2 = Pair.swapPairs(pair2);
        Pair.printPair(swappedPair2);


    }
}