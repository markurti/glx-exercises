package org.example.Entity;

public class Helicopter implements AbstractVehicle {
    @Override
    public void start() {
        System.out.println("Helicopter started.");
    }

    @Override
    public void stop() {
        System.out.println("Helicopter stopped.");
    }

    @Override
    public void accelerate() {
        System.out.println("Helicopter accelerating...");
    }

    @Override
    public void brake() {
        System.out.println("Helicopter braking...");
    }
}
