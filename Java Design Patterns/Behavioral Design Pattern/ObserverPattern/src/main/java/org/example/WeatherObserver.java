package org.example;

public class WeatherObserver implements Observer {
    private String name;

    public WeatherObserver(String name) {
        this.name = name;
    }

    @Override
    public void update(double temperature, double humidity, double pressure) {
        System.out.println("[" + name + "] Weather Update:");
        System.out.println("  Temperature: " + temperature + "°C");
        System.out.println("  Humidity: " + humidity + "%");
        System.out.println("  Pressure: " + pressure + " hPa");
        System.out.println();
    }

    public String getName() {
        return name;
    }
}
