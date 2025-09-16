package org.example;

import org.example.AbstractFactory.AbstractVehicleFactory;
import org.example.AbstractFactory.SubFactory.AirVehicleFactory;
import org.example.AbstractFactory.SubFactory.LandVehicleFactory;
import org.example.AbstractFactory.SubFactory.WaterVehicleFactory;
import org.example.Entity.AbstractVehicle;

import java.util.Scanner;

public class VehicleFactoryClient {
    private static AbstractVehicleFactory getFactory(String vehicleType) {
        return switch (vehicleType.toLowerCase()) {
            case "car", "bike" -> new LandVehicleFactory();
            case "boat", "submarine" -> new WaterVehicleFactory();
            case "airplane", "helicopter" -> new AirVehicleFactory();
            default -> throw new IllegalArgumentException("Invalid vehicle type: " + vehicleType);
        };
    }

    private static AbstractVehicle createVehicle(AbstractVehicleFactory factory, String vehicleType) {

        return switch (vehicleType.toLowerCase()) {
            case "car" -> factory.createCar();
            case "bike" -> factory.createBike();
            case "boat" -> factory.createBoat();
            case "submarine" -> factory.createSubmarine();
            case "airplane" -> factory.createAirplane();
            case "helicopter" -> factory.createHelicopter();
            default -> throw new IllegalArgumentException("Invalid vehicle type: " + vehicleType);
        };
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueProgram = true;

        System.out.println("Program start");

        while (continueProgram) {
            try {
                System.out.println("Enter vehicle type: ");
                System.out.println("(Car, Bike, Boat, Submarine, Airplane, Helicopter)");
                String vehicleType = scanner.nextLine().trim();

                if (vehicleType.isEmpty()) {
                    System.out.println("Invalid vehicle type");
                    continue;
                }

                AbstractVehicle vehicle = createVehicle(getFactory(vehicleType), vehicleType);
                System.out.println(vehicleType + " created.");
                vehicle.start();
                vehicle.accelerate();
            } catch (IllegalArgumentException | UnsupportedOperationException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }

            System.out.print("Do you want to continue? (Y/N): ");
            String continueChoice = scanner.nextLine().trim().toLowerCase();
            continueProgram = continueChoice.equals("y") || continueChoice.equals("yes");
        }

        System.out.println("Program end");
        scanner.close();
    }
}
