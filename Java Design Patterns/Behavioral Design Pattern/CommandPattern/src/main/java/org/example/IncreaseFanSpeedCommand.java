package org.example;

public class IncreaseFanSpeedCommand implements Command {
    private Fan fan;

    public IncreaseFanSpeedCommand(Fan fan) {
        this.fan = fan;
    }

    @Override
    public void execute() {
        fan.increaseSpeed();
    }
}
