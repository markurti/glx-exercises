package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create String, Integer pair
        Pair<String, Integer> pair1 = new Pair<>("abc", 123);
        Pair<Boolean, Integer> pair2 = new Pair<>(true, 456);

        // Test swap and print methods
        Pair.printPair(pair2);
        Pair<Integer, Boolean> swappedPair2 = Pair.swapPairs(pair2);
        Pair.printPair(swappedPair2);

        // ArrayList Pair
        Pair<List<Integer>, List<Integer>> arrayListPair = new Pair<>(new ArrayList<>(), new ArrayList<>());
    }
}