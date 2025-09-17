package org.example;

public interface TextCharacter {
    void render(Point position, String fontSize, String color);
    void edit(String content);
    String getCharacter();
}
