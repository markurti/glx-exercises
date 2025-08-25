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


    }
}