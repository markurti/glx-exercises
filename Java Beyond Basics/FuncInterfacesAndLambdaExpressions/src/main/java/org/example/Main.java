package org.example;

public class Main {
    public static void main(String[] args) {
        // Sample menu items
        MenuItem pizza = new MenuItem("Margherita Pizza", 15.99, "MAIN_COURSE");
        MenuItem salad = new MenuItem("Caesar Salad", 8.50, "APPETIZER");
        MenuItem tiramisu = new MenuItem("Tiramisu", 6.75, "DESSERT");
        MenuItem coffee = new MenuItem("Espresso", 3.25, "BEVERAGE");
        MenuItem mysteryItem = new MenuItem("Special Dish", 12.00, "");
        MenuItem unknownItem = new MenuItem("Chef's Surprise", 18.00, "EXOTIC");
    }
}