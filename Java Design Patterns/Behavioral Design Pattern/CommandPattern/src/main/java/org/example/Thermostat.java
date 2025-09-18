package org.example;

public class Thermostat {
    private int temperature = 20;

    public void setTemperature(int temp) {
        this.temperature = temp;
        System.out.println("Thermostat temperature set to: " + temperature + "°C");
    }
}
