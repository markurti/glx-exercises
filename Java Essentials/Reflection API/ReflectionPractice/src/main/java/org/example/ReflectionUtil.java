package org.example;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

public class ReflectionUtil {
    public static void printFieldNamesAndVales(Object obj) throws IllegalAccessException {
        Class<?> objClass = obj.getClass();
        Field[] fields = objClass.getDeclaredFields();

        // Print field names and their values
        System.out.println("The field name:value pairs for the given object are: ");
        for (Field field : fields) {
            // Set field accessible (if it was private it would throw an IllegalAccessException)
            field.setAccessible(true);
            System.out.println(field.getName() + ":" + field.get(obj));
        }
    }
}
