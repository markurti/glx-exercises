package org.example;

public interface TrafficLightState {
    void transition(TrafficLight context);
    void display();
}
