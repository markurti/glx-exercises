package org.example;

public class Product {
    private final int id;
    private final String name;
    private final int stock;
    private static int incrementId = 0;

    public Product(String name, int stock) {
        this.id = ++incrementId;
        this.name = name;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }
}
