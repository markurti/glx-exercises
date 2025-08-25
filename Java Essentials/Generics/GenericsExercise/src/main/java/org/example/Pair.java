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

    public static <E, S> Pair<E, S> swapPairs(Pair<S, E> pair) {
        return new Pair<>(pair.getSecondPair(), pair.getFirstPair());
    }

    public static <S, E> void printPair(Pair<S, E> pair) {
        System.out.println(pair.getFirstPair() + ", " + pair.getSecondPair());
    }
}
