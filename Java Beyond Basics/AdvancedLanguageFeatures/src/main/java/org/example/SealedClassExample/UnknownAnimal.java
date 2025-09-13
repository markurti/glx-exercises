package org.example.SealedClassExample;

public final class UnknownAnimal extends Animal {
    @Override
    public String sound() {
        return "???";
    }
}
