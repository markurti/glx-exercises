package org.example;

public class Fan {
    private int speed = 0;

    public void increaseSpeed() {
        speed++;
        System.out.println("Fan speed increased to: " + speed);
    }

    public void decreaseSpeed() {
        if (speed > 0) {
            speed--;
            System.out.println("Fan speed decreased to: " + speed);
        } else {
            System.out.println("Fan is already at minimum speed");
        }
    }
}
