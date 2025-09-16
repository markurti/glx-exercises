package org.example;

import java.util.Hashtable;

public class ShapeRegistry {
    private static Hashtable<Integer, Shape> shapeMap = new Hashtable<>();

    public static Shape getShape(int shapeId) {
        Shape cachedShape = shapeMap.get(shapeId);
        return (Shape) cachedShape.clone();
    }

    // Load some prototypes into the cache
    public static void loadCache() {
        Circle circle = new Circle();
        circle.setId(1);
        shapeMap.put(circle.getId(), circle);

        Rectangle rectangle = new Rectangle();
        rectangle.setId(2);
        shapeMap.put(rectangle.getId(), rectangle);

        Circle circle2 = new Circle();
        circle2.setId(3);
        shapeMap.put(circle2.getId(), circle2);

        Rectangle rectangle2 = new Rectangle();
        rectangle2.setId(4);
        shapeMap.put(rectangle2.getId(), rectangle2);
    }
}
