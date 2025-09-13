package org.example;

import org.example.SealedClassExample.Animal;
import org.example.SealedClassExample.Elephant;
import org.example.SealedClassExample.Lion;
import org.example.SealedClassExample.UnknownAnimal;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        System.out.println("Test Older Person Finder");
        List<Person> people = Arrays.asList(
                new Person("Alice", 25),
                new Person("Bob", 30),
                new Person("Charlie", 35),
                new Person("Diana", 28),
                new Person("Mark", 35)
        );

        if (OldestPersonFinder.findOldestPerson(people).isPresent()) {
            System.out.println("Found oldest person: " + OldestPersonFinder.findOldestPerson(people).get());
        }

        System.out.println("Test Person to JSON string converter");
        String JSONdata = PersonToJsonConverter.SendPersonsToJSON(people);
        System.out.println(JSONdata);

        System.out.println("Test Product methods");
        // Create sample products
        List<Product> products = Arrays.asList(
                new Product("Laptop", 999.99, 2),
                new Product("Mouse", 29.99, 5),
                new Product("Keyboard", 79.99, 3),
                new Product("Monitor", 299.99, 1)
        );

        System.out.println("Products:");
        products.forEach(System.out::println);

        double totalValue = ProductUtils.getTotalValue(products);
        System.out.println("Total value: " + totalValue);

        Optional<Product> mostExpensiveProduct = ProductUtils.getMostExpensiveProduct(products);
        if (mostExpensiveProduct.isPresent()) {
            System.out.println("Most expensive product: " + mostExpensiveProduct.get());
        } else {
            System.out.println("No most expensive product found: list is empty");
        }

        Animal animal = new Animal();
        Animal lion = new Lion();
        Animal elephant = new Elephant();
        Animal unknownAnimal = new UnknownAnimal();
        System.out.println("Sealed class test:");
        System.out.println("Animal sound: " + animal.sound());
        System.out.println("Lion sound: " + lion.sound());
        System.out.println("Elephant sound: " + elephant.sound());
        System.out.println("Unknown animal sound: " + unknownAnimal.sound());
    }
}