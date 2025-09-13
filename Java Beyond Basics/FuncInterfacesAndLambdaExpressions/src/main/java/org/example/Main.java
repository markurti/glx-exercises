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

        ApplySpecialOffer offerApplier = (menuItem) -> {
            String category = categorizer.categorize(menuItem);

            // Handle unknown categories
            if (category == null) {
                System.out.println("Unknown category for " + menuItem.getName() + ". Not discount applied.");

                return new MenuItem(menuItem.getName(), menuItem.getPrice(), "UNKNOWN");
            }

            double newPrice = menuItem.getPrice();
            String offerDescription = "";

            switch (category) {
                case "APPETIZER":
                    newPrice = menuItem.getPrice() * 0.85; // 15% discount
                    offerDescription = " (15% Happy Hour Discount)";
                    break;
                case "MAIN_COURSE":
                    if (menuItem.getPrice() > 15.00) {
                        newPrice = menuItem.getPrice() * 0.90; // 10% discount for expensive mains
                        offerDescription = " (10% Premium Dish Discount)";
                    }
                    break;
                case "DESSERT":
                    newPrice = menuItem.getPrice() * 0.80; // 20% discount
                    offerDescription = " (20% Sweet Treats Offer)";
                    break;
                case "BEVERAGE":
                    if (menuItem.getPrice() < 5.00) {
                        newPrice = menuItem.getPrice() * 0.95; // 5% discount for cheap beverages
                        offerDescription = " (5% Coffee Break Discount)";
                    }
                    break;
            }

            return new MenuItem(menuItem.getName() + offerDescription, newPrice, offerDescription);
        };

        System.out.println("RESTAURANT MENU MANAGEMENT SYSTEM");
        MenuItem[] menuItems = {pizza, salad, tiramisu, coffee, mysteryItem, unknownItem};

        for (MenuItem menuItem : menuItems) {
            System.out.println("Original item: " + menuItem);

            try {
                // Categorize with default handling
                String category = categorizer.categorizeWithDefault(menuItem, "SPECIAL");
                System.out.println("Categorized as: " + category);

                // Apply speical offers
                MenuItem discountedItem = offerApplier.applyOffer(menuItem);
                System.out.println("After Offers: " + discountedItem);

                double savings = menuItem.getPrice() - discountedItem.getPrice();
                if (savings > 0) {
                    System.out.println("Savings: $" + String.format("%.2f", savings));
                }
            } catch (Exception e) {
                System.out.println("Error processing item: " + e.getMessage());
            }

            System.out.println("-----------------------------");
        }
    }
}