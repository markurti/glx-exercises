package org.example;

public class Product {
    private int id;
    private String name;
    private int stock;
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
