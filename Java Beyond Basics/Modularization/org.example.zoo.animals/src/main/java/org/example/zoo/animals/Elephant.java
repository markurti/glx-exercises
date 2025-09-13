package org.example.zoo.animals;

public final class Elephant extends Animal {
    @Override
    public String sound() {
        return "TUUUT!";
    }

    @Override
    public String toString() {
        return Elephant.class.getSimpleName();
    }
}
