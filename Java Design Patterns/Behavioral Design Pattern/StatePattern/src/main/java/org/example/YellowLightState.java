package org.example;

public class YellowLightState implements TrafficLightState {
    @Override
    public void transition(TrafficLight context) {
        System.out.println("Transitioning from Yellow to Red");
        context.setState(new RedLightState());
    }

    @Override
    public void display() {
        System.out.println("YELLOW LIGHT - CAUTION");
    }
}
