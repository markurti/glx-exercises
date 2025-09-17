package org.example;

import java.util.ArrayList;
import java.util.List;

public class Directory implements FileSystemComponent {
    private String name;
    private List<FileSystemComponent> components;

    public Directory(String name) {
        this.name = name;
        this.components = new ArrayList<>();
    }

    public void add(FileSystemComponent component) {
        components.add(component);
    }

    public void remove(FileSystemComponent component) {
        components.remove(component);
    }


    @Override
    public int getSize() {
        int totalSize = 0;
        for (FileSystemComponent component : components) {
            totalSize += component.getSize();
        }
        return totalSize;
    }

    @Override
    public void printDetails(String indent) {
        System.out.println(indent + "Directory: " + name + " (" + getSize() + " mb)");
        for (FileSystemComponent component : components) {
            component.printDetails(indent + "  ");
        }
    }

    @Override
    public String getName() {
        return name;
    }
}
