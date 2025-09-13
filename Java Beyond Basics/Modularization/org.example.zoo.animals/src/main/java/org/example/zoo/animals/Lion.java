package org.example.zoo.animals;

public final class Lion extends Animal {
    @Override
    public String sound() {
        return "ROAR!";
    }

    @Override
    public String toString() {
        return Lion.class.getSimpleName();
    }
}
