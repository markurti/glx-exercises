package org.example;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
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

    public static void invokePrivateMethod(Object obj, String methodName, Object... args) throws InvocationTargetException, IllegalAccessException {
        Class<?> objClass = obj.getClass();
        Method[] methods = objClass.getDeclaredMethods();

        // If method doesn't exist in given object -> return error message
        if (Arrays.stream(methods).noneMatch(method -> method.getName().equals(methodName))) {
            System.out.println("There is no method named: " + methodName + ".");
            return;
        }

        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                // If method is public -> return error message
                if (method.getModifiers() == Modifier.PUBLIC) {
                    System.out.println("The method " + methodName + " is public. Please provide a private method name.");
                    return;
                }
                method.setAccessible(true);
                method.invoke(obj, args);
                return;
            }
        }
    }
}
