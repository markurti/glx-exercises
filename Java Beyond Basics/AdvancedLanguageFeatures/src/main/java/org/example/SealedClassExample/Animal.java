package org.example.SealedClassExample;

public sealed class Animal permits Lion, Elephant, UnknownAnimal {
    public String sound() {
        return "Animal_Sound";
    }
}
