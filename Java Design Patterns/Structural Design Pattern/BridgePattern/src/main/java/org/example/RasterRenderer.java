package org.example;

public class RasterRenderer implements Renderer {

    @Override
    public void renderCircle(double radius) {
        System.out.println("Drawing Circle with radius " + radius + " using Raster rendering");
    }

    @Override
    public void renderSquare(double side) {
        System.out.println("Drawing Square with side " + side + " using Raster rendering");
    }
}
