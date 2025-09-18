package org.example;

public class GreenLightState implements TrafficLightState {
    @Override
    public void transition(TrafficLight context) {
        System.out.println("Transitioning from Green to Yellow");
        context.setState(new YellowLightState());
    }

    @Override
    public void display() {
        System.out.println("GREEN LIGHT - GO");
    }
}
