package org.example;

public class Client {
    public static void main(String[] args) {
        // Create renderers
        Renderer rasterRenderer = new RasterRenderer();
        Renderer vectorRenderer = new VectorRenderer();

        // Create shapes with different renderers
        Shape rasterCircle = new Circle(rasterRenderer, 5.0);
        Shape vectorCircle = new Circle(vectorRenderer, 3.0);
        Shape rasterSquare = new Square(rasterRenderer, 4.0);
        Shape vectorSquare = new Square(vectorRenderer, 6.0);

        // Draw shapes
        System.out.println("Bridge Pattern Demo:");
        System.out.println("-------------------");

        rasterCircle.draw();
        vectorCircle.draw();
        rasterSquare.draw();
        vectorSquare.draw();
    }
}