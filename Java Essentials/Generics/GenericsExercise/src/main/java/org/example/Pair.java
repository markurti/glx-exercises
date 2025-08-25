package org.example;

public class Pair<K, V> {
    private final K firstPair;
    private final V secondPair;

    public Pair(K firstPair, V secondPair) {
        this.firstPair = firstPair;
        this.secondPair = secondPair;
    }

    public K getFirstPair() {
        return firstPair;
    }

    public V getSecondPair() {
        return secondPair;
    }

    public Pair<V, K> swapPairs(Pair<K, V> pair) {
        return new Pair<>(pair.getSecondPair(), pair.getFirstPair());
    }

    public void printPair(Pair<K, V> pair) {
        System.out.println(pair.getFirstPair() + ", " + pair.getSecondPair());
    }
}
