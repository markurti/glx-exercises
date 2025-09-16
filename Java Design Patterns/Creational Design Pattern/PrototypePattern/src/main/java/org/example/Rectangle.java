package org.example;

public class Rectangle implements Shape {
    private final String type = "Rectangle";
    private int id;

    @Override
    public void draw() {
        System.out.println("Drawing " + type);
    }

    @Override
    public String getType() {
        return type;
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
