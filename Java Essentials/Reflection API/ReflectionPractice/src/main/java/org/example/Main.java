package org.example;

public class Main {
    public static void main(String[] args) {
        Person person = new Person("Mark", 21);

        try {
            ReflectionUtil.printFieldNamesAndVales(person);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot access field values of the given object: " + e.getMessage());
        }
    }
}