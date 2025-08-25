package org.example;

import java.util.ArrayList;
import java.util.List;

public class Shipment<T> {
    private final List<Box<T>> boxes;

    public Shipment() {
        boxes = new ArrayList<Box<T>>();
    }

    public void addBox(Box<T> box) {
        boxes.add(box);
    }

    public <E> boolean inspectShipment(E objectOfAnyType) {
        for (Box<T> box : boxes) {
            if (box.inspect(objectOfAnyType)) {
                return true;
            }
        }
        return false;
    }
}
