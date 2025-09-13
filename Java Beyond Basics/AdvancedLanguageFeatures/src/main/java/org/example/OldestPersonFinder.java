package org.example;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class OldestPersonFinder {
    public static Optional<String> findOldestPerson(List<Person> people) {
        Optional<String> oldestPerson = people.stream()
                .max(Comparator.comparing(Person::getAge))
                .map(Person::getName);

        if (oldestPerson.isPresent()) {
            return oldestPerson;
        } else {
            return Optional.empty();
        }
    }
}
