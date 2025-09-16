package org.example;

public class Circle implements Shape {
    private final String type = "Circle";
    private int id;

    @Override
    public void draw() {
        System.out.println("Drawing " + type);
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Object clone() {
        Object clone = null;

        try {
            clone = super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("Cloning failed: " + e.getMessage());
        }

        return clone;
    }
}
