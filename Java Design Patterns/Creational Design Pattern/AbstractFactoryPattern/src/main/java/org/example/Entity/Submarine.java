package org.example.Entity;

public class Submarine implements AbstractVehicle {
    @Override
    public void start() {
        System.out.println("Submarine started.");
    }

    @Override
    public void stop() {
        System.out.println("Submarine stopped.");
    }

    @Override
    public void accelerate() {
        System.out.println("Submarine accelerating...");
    }

    @Override
    public void brake() {
        System.out.println("Submarine braking...");
    }
}
