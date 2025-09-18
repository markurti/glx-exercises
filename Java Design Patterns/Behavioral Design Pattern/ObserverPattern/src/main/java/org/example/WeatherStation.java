package org.example;

import java.util.ArrayList;
import java.util.List;

public class WeatherStation implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private double temperature;
    private double humidity;
    private double pressure;

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
        if (observer instanceof WeatherObserver) {
            System.out.println(((WeatherObserver) observer).getName() + " subscribed to weather updates");
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
        if (observer instanceof WeatherObserver) {
            System.out.println(((WeatherObserver) observer).getName() + " unsubscribed from weather updates");
        }
    }

    @Override
    public void notifyObservers() {
        System.out.println("=== Broadcasting Weather Update ===");
        for (Observer observer : observers) {
            observer.update(temperature, humidity, pressure);
        }
    }

    public void setState(double temperature, double humidity, double pressure) {
        System.out.println("Weather Station: New readings received");
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        notifyObservers();
    }

    // Individual setters for partial updates
    public void setTemperature(double temperature) {
        this.temperature = temperature;
        System.out.println("Temperature changed to: " + temperature + "°C");
        notifyObservers();
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
        System.out.println("Humidity changed to: " + humidity + "%");
        notifyObservers();
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
        System.out.println("Pressure changed to: " + pressure + " hPa");
        notifyObservers();
    }
}
