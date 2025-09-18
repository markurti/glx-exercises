package org.example;

public class RedLightState implements TrafficLightState {
        @Override
        public void transition(TrafficLight context) {
            System.out.println("Transitioning from Red to Green");
            context.setState(new GreenLightState());
        }

        @Override
        public void display() {
            System.out.println("RED LIGHT - STOP");
        }
}
