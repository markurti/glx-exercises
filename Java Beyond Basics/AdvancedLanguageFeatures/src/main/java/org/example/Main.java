package org.example;

import java.util.Arrays;
import java.util.List;

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


    }
}