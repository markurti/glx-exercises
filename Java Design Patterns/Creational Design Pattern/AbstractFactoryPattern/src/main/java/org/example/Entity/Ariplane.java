package org.example.Entity;

public class Ariplane implements AbstractVehicle {
    @Override
    public void start() {
        System.out.println("Airplane started.");
    }

    @Override
    public void stop() {
        System.out.println("Airplane stopped.");
    }

    @Override
    public void accelerate() {
        System.out.println("Airplane accelerating...");
    }

    @Override
    public void brake() {
        System.out.println("Airplane braking...");
    }
}
