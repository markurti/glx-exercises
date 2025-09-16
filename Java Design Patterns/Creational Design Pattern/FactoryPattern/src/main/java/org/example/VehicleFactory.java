package org.example;

public class VehicleFactory {
    public static Vehicle getVehicle(String type) {
        return switch (type.toLowerCase()) {
            case "car" -> new Car();
            case "truck" -> new Truck();
            case "bike" -> new Bike();
            default -> throw new IllegalArgumentException("Invalid vehicle type: " + type);
        };
    }
}
