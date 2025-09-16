package org.example.Entity;

public class Boat implements AbstractVehicle {
    @Override
    public void start() {
        System.out.println("Boat started.");
    }

    @Override
    public void stop() {
        System.out.println("Boat stopped.");
    }

    @Override
    public void accelerate() {
        System.out.println("Boat accelerating...");
    }

    @Override
    public void brake() {
        System.out.println("Boat braking...");
    }
}
