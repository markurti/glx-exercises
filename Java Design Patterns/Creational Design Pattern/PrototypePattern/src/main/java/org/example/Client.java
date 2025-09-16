package org.example;

public class Client {
    public static void main(String[] args) {
        ShapeRegistry.loadCache();

        Shape clonedCircle = ShapeRegistry.getShape(1);
        System.out.println("Shape: " + clonedCircle.getType());
        clonedCircle.draw();

        Shape clonedRectangle = ShapeRegistry.getShape(2);
        System.out.println("Shape: " + clonedRectangle.getType());
        clonedRectangle.draw();

        Shape clonedCircle2 = ShapeRegistry.getShape(3);
        System.out.println("Shape: " + clonedCircle2.getType());
        clonedCircle2.draw();

        Shape clonedRectangle2 = ShapeRegistry.getShape(4);
        System.out.println("Shape: " + clonedRectangle2.getType());
        clonedRectangle2.draw();
    }
}