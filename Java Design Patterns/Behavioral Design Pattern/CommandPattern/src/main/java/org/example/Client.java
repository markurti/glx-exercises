package org.example;

public class Client {
    public static void main(String[] args) {
        // Create devices
        Light light = new Light();
        Fan fan = new Fan();
        Thermostat thermostat = new Thermostat();

        // Create remote control
        RemoteControlInvoker remote = new RemoteControlInvoker();

        System.out.println("=== Smart Home Device Control ===");

        // Light control
        System.out.println("\nControlling Light:");
        remote.setCommand(new TurnOnLightCommand(light));
        remote.pressButton();

        remote.setCommand(new TurnOffLightCommand(light));
        remote.pressButton();

        // Fan control
        System.out.println("\nControlling Fan:");
        remote.setCommand(new IncreaseFanSpeedCommand(fan));
        remote.pressButton();
        remote.pressButton();

        remote.setCommand(new DecreaseFanSpeedCommand(fan));
        remote.pressButton();

        // Thermostat control
        System.out.println("\nControlling Thermostat:");
        remote.setCommand(new SetTemperatureCommand(thermostat, 25));
        remote.pressButton();

        remote.setCommand(new SetTemperatureCommand(thermostat, 18));
        remote.pressButton();
    }
}