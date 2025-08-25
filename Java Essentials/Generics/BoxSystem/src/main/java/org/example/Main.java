package org.example;

public class Main {
    public static void main(String[] args) {
        // Create a box
        Box<String> box = new Box<>("surprise");

        // Compare it with different objects
        System.out.println("Compare object in the box with different objects:");
        System.out.println(box.inspect("another string"));
        System.out.println(box.inspect(123));
        System.out.println(box.inspect(false));

        // Create more boxes
        Box<Integer> box1 = new Box<>(123);
        Box<Boolean> box2 = new Box<>(false);

        // Create a shipment
        Shipment shipment = new Shipment();
        shipment.addBox(box);
        shipment.addBox(box1);
        shipment.addBox(box2);

        // Check if shipment can take different objects
        System.out.println("\nCheck if shipment can take different objects:");
        System.out.println(shipment.inspectShipment(false));
        System.out.println(shipment.inspectShipment(123));
        System.out.println(shipment.inspectShipment("abc"));
        System.out.println(shipment.inspectShipment('z'));
    }
}