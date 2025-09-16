package org.example.Entity;

public class Bike implements AbstractVehicle {
    @Override
    public void start() {
        System.out.println("Bike started.");
    }

    @Override
    public void stop() {
        System.out.println("Bike stopped.");
    }

    @Override
    public void accelerate() {
        System.out.println("Bike accelerating...");
    }

    @Override
    public void brake() {
        System.out.println("Bike braking...");
    }
}
