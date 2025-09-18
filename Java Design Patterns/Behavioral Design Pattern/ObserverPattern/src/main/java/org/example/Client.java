package org.example;

public class Client {
    public static void main(String[] args) {
        WeatherStation station = new WeatherStation();

        System.out.println("=== Weather Station Observer Pattern Demo ===\n");

        // Create observers
        WeatherObserver mobileApp = new WeatherObserver("Mobile App");
        WeatherObserver website = new WeatherObserver("Weather Website");
        WeatherObserver tvStation = new WeatherObserver("TV Weather Channel");

        // Subscribe observers
        System.out.println("--- Subscribing Observers ---");
        station.addObserver(mobileApp);
        station.addObserver(website);
        station.addObserver(tvStation);

        System.out.println("\n--- Initial Weather Reading ---");
        station.setState(25.5, 65.0, 1013.2);

        System.out.println("--- Weather Change ---");
        station.setState(22.0, 70.0, 1008.5);

        System.out.println("--- Unsubscribing One Observer ---");
        station.removeObserver(website);

        System.out.println("--- Another Weather Update ---");
        station.setState(28.0, 45.0, 1020.1);

        System.out.println("--- Partial Update (Temperature Only) ---");
        station.setTemperature(30.5);
    }
}