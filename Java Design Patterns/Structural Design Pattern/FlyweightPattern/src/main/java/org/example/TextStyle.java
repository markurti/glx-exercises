package org.example;

public enum TextStyle {
    BOLD("Bold"),
    ITALIC("Italic"),
    NORMAL("Normal"),
    UNDERLINE("Underline");

    private String styleName;

    TextStyle(String styleName) {
        this.styleName = styleName;
    }

    public String getStyleName() {
        return styleName;
    }
}