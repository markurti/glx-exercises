package org.example;

import java.util.ArrayList;
import java.util.List;

public class Shipment {
    private final List<Box<?>> boxes;

    public Shipment() {
        boxes = new ArrayList<>();
    }

    public void addBox(Box<?> box) {
        boxes.add(box);
    }

    public <E> boolean inspectShipment(E objectOfAnyType) {
        for (Box<?> box : boxes) {
            if (box.inspect(objectOfAnyType)) {
                return true;
            }
        }
        return false;
    }
}
