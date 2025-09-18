package org.example;

public class DecreaseFanSpeedCommand implements Command {
    private Fan fan;

    public DecreaseFanSpeedCommand(Fan fan) {
        this.fan = fan;
    }

    @Override
    public void execute() {
        fan.decreaseSpeed();
    }
}
