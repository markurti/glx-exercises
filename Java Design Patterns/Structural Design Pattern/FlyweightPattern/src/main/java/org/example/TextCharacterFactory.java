package org.example;

import java.util.HashMap;
import java.util.Map;

public class TextCharacterFactory {
    private static Map<String, TextCharacter> flyweights = new HashMap<>();

    public static TextCharacter getCharacter(char character, TextStyle style) {
        String key = character + "_" + style.name();

        TextCharacter flyweight = flyweights.get(key);
        if (flyweight == null) {
            flyweight = new TextCharacterImpl(character, style);
            flyweights.put(key, flyweight);
            System.out.println("Created new flyweight. Total flyweights: " + flyweights.size());
        } else {
            System.out.println("Reusing existing flyweight for: '" + character + "'");
        }

        return flyweight;
    }

    public static int getFlyweightCount() {
        return flyweights.size();
    }

    public static void printFlyweightStats() {
        System.out.println("\nFlyweight Statistics:");
        System.out.println("Total flyweights created: " + flyweights.size());
    }
}
