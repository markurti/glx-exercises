package org.example;

public interface Shape extends Cloneable {
    public String getType();
    public int getId();
    public void setId(int id);
    public void draw();
    public Object clone();
}
