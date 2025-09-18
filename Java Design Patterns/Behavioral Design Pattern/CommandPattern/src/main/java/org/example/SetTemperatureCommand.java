package org.example;

public class SetTemperatureCommand implements Command {
    private Thermostat thermostat;
    private int temperature;

    public SetTemperatureCommand(Thermostat thermostat, int temperature) {
        this.thermostat = thermostat;
        this.temperature = temperature;
    }

    @Override
    public void execute() {
        thermostat.setTemperature(temperature);
    }
}
