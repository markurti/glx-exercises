package org.example.zoo.animals;

public final class Giraffe extends Animal {
    @Override
    public String sound() {
        return "Humm!";
    }

    @Override
    public String toString() {
        return Giraffe.class.getSimpleName();
    }
}
