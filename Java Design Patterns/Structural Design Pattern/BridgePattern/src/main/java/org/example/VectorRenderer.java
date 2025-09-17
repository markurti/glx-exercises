package org.example;

public class VectorRenderer implements Renderer {
    @Override
    public void renderCircle(double radius) {
        System.out.println("Drawing Circle with radius " + radius + " using Vector rendering");
    }

    @Override
    public void renderSquare(double side) {
        System.out.println("Drawing Square with side " + side + " using Vector rendering");
    }
}
