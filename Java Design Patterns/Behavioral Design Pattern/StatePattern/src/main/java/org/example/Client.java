package org.example;

public class Client {
    public static void main(String[] args) {
        TrafficLight trafficLight = new TrafficLight();

        System.out.println("=== Traffic Light State Pattern Demo ===\n");

        // Simulate traffic light cycles
        for (int cycle = 1; cycle <= 3; cycle++) {
            System.out.println("--- Cycle " + cycle + " ---");

            // Display current state and transition 3 times (Red -> Green -> Yellow -> Red)
            for (int i = 0; i < 3; i++) {
                trafficLight.displayCurrentState();

                try {
                    Thread.sleep(1000); // Wait 1 second
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                trafficLight.performStateTransition();
                System.out.println();
            }
        }

        // Final state
        System.out.println("--- Final State ---");
        trafficLight.displayCurrentState();
    }
}