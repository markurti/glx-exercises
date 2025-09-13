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

        CategorizeMenuItem categorizer = (menuItem) -> {
            String name = menuItem.getName().toLowerCase();
            String currentCategory = menuItem.getCategory();

            // If category is already set and valid, return it
            if (currentCategory != null && !currentCategory.trim().isEmpty()) {
                switch (currentCategory.toUpperCase()) {
                    case "APPETIZER":
                    case "MAIN_COURSE":
                    case "DESSERT":
                    case "BEVERAGE":
                        return currentCategory.toUpperCase();
                }
            }

            // Categorize based on name if category is empty or unknown
            if (name.contains("pizza") || name.contains("pasta") || name.contains("steak")) {
                return "MAIN_COURSE";
            } else if (name.contains("salad") || name.contains("soup") || name.contains("starter")) {
                return "APPETIZER";
            } else if (name.contains("cake") || name.contains("ice cream") || name.contains("tiramisu")) {
                return "DESSERT";
            } else if (name.contains("coffee") || name.contains("tea") || name.contains("juice")) {
                return "BEVERAGE";
            }

            // For unknown categories
            return null;
        };


    }
}