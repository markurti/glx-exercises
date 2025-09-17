package org.example;

import java.util.ArrayList;
import java.util.List;

public class Document {
    private List<DocumentCharacter> characters = new ArrayList<>();

    public void addCharacter(char ch, TextStyle style, Point position, String fontSize, String color) {
        TextCharacter flyweight = TextCharacterFactory.getCharacter(ch, style);
        DocumentCharacter docChar = new DocumentCharacter(flyweight, position, fontSize, color);
        characters.add(docChar);
    }

    public void renderDocument() {
        System.out.println("\nRendering document:");
        System.out.println("-------------------");
        for (DocumentCharacter docChar : characters) {
            docChar.render();
        }
    }

    public int getCharacterCount() {
        return characters.size();
    }
}
