package org.example;

public interface Shape extends Cloneable {
    public String getType();
    public void draw();
    public Object clone();
}
