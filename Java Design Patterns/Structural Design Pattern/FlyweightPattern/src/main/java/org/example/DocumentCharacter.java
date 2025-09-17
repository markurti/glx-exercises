package org.example;

public class DocumentCharacter {
    private TextCharacter flyweight; // Reference to flyweight
    private Point position; // Extrinsic state
    private String fontSize; // Extrinsic state
    private String color; // Extrinsic state

    public DocumentCharacter(TextCharacter flyweight, Point position, String fontSize, String color) {
        this.flyweight = flyweight;
        this.position = position;
        this.fontSize = fontSize;
        this.color = color;
    }

    public void render() {
        flyweight.render(position, fontSize, color);
    }

    public void edit(String content) {
        flyweight.edit(content);
    }

    // Extrinsic state methods
    public void setPosition(Point position) {
        this.position = position;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
