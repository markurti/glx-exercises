package org.example;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) {
        Person person = new Person("Mark", 21);

        try {
            ReflectionUtil.printFieldNamesAndVales(person);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot access field values of the given object: " + e.getMessage());
        }

        try {
            ReflectionUtil.invokePrivateMethod(person, "sayHello");
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Cannot invoke method: " + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot access method: " + e.getMessage());
        }
    }
}