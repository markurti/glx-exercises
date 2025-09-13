package org.example.zoo.animals;

public sealed class Animal permits Lion, Elephant, Giraffe {
    public String sound() {
        return "BOO!";
    }

    @Override
    public String toString() {
        return Animal.class.getSimpleName();
    }
}
