package org.example;

public class File implements FileSystemComponent {
    private String name;
    private int size;

    public File(String name, int size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void printDetails(String indent) {
        System.out.println(indent + "File: " + name + " (" + size + " mb)");
    }

    @Override
    public String getName() {
        return name;
    }
}
