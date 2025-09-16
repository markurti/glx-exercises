package org.example;

public class Client {
    public static void main(String[] args) {
        System.out.println("Building basic car...");
        Car basicCar = new Car.CarBuilder()
                .withBrand("Ford")
                .withModel("Fiesta")
                .withColor("Red")
                .withEngine("1.6L 4-Cyl")
                .withTransmission("Manual 5 Speed")
                .withFuelType("Gasoline")
                .build();

        System.out.println("Basic car built: " + basicCar);

        System.out.println("=" + "=".repeat(30));

        System.out.println("Building luxury car...");
        Car luxuryCar = new Car.CarBuilder()
                .withBrand("BMW")
                .withModel("M3 Touring")
                .withColor("Grey")
                .withEngine("3.0 I6")
                .withTransmission("8-Speed Dual-Clutch")
                .withFuelType("Gasoline")
                .build();

        System.out.println("Luxury car built: " + luxuryCar);
        System.out.println("=" + "=".repeat(30));

        System.out.println("Building scrap car...");
        Car scrapCar = new Car.CarBuilder()
                .withBrand("Toyota")
                .withModel("Corolla")
                .withColor("Rusty")
                .withFuelType("Diesel")
                .build();

        System.out.println("Scrap car built: " + scrapCar);
    }
}