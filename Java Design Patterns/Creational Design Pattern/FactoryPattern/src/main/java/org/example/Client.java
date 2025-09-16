package org.example;

import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                System.out.print("Enter vehicle type (car/truck/bike): ");
                String vehicleType = scanner.nextLine();

                Vehicle vehicle = VehicleFactory.getVehicle(vehicleType);

                System.out.println(vehicle);
                vehicle.start();
                vehicle.accelerate();

                System.out.println("Do you want to continue? (Y/N)");
                String choice = scanner.nextLine();
                if (choice.equals("N") || choice.equals("n")) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
