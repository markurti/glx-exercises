package org.example;

public class TextCharacterImpl implements TextCharacter {
    private final char character;
    private final TextStyle style;

    public TextCharacterImpl(char character, TextStyle style) {
        this.character = character;
        this.style = style;
        System.out.println("Creating flyweight for: '" + character + "' with style: " + style.getStyleName());
    }


    @Override
    public void render(Point position, String fontSize, String color) {
        System.out.println("Rendering '" + character + "' at position " + position +
                " with style: " + style.getStyleName() + ", size: " + fontSize + ", color: " + color);
    }

    @Override
    public void edit(String content) {
        System.out.println("Editing character '" + character + "' with content: " + content);
    }

    @Override
    public String getCharacter() {
        return String.valueOf(character);
    }

    public char getChar() {
        return character;
    }

    public TextStyle getStyle() {
        return style;
    }
}
