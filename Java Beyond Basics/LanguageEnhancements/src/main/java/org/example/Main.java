package org.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("Online Shopping Platform Demo");

        // Create items
        Item laptop = new Item("Gaming Laptop", 899.99);
        Item mouse = new Item("Wireless Mouse", 29.99);
        Item keyboard = new Item("Mechanical Keyboard", 149.99);
        Item monitor = new Item("4K Monitor", 299.99);
        Item headphones = new Item("Gaming Headphones", 79.99);

        // Create cart and processor
        Cart cart = new Cart();
        OrderProcessor processor = new OrderProcessor() {};

        // Cart operations
        System.out.println("Adding items to cart");
        processor.addItemToCart(cart, laptop);
        processor.addItemToCart(cart, mouse);
        processor.addItemToCart(cart, keyboard);
        processor.addItemToCart(cart, monitor);

        processor.displayCartSummary(cart);

        // Test different discount types
        System.out.println("Different discount types:");

        // Percentage discount
        OrderSummary summary1 = processor.processOrder(cart, "percentage", 15.0);
        System.out.println(summary1);

        // Add more items for BOGO demo
        processor.addItemToCart(cart, headphones);
        processor.addItemToCart(cart, new Item("Mouse Pad", 19.99));

        // BOGO discount
        OrderSummary summary2 = processor.processOrder(cart, "bogo");
        System.out.println(summary2);

        // Tiered discount
        OrderSummary summary3 = processor.processOrder(cart, "tiered");
        System.out.println(summary3);

        // Seasonal discount
        OrderSummary summary4 = processor.processOrder(cart, "seasonal", "winter");
        System.out.println(summary4);

        // Custom discount showcase
        System.out.println("Premium discount showcase:");
        PremiumOrderProcessor premiumProcessor = new PremiumOrderProcessor();

        // Standard discount comparison
        Cart smallCart = new Cart();
        premiumProcessor.addItemToCart(smallCart, mouse);
        premiumProcessor.addItemToCart(smallCart, new Item("Cable", 15.99));

        System.out.println("Regular customer standard discount:");
        OrderProcessor regular = new OrderProcessor() {};
        OrderSummary regularSummary = regular.processOrder(smallCart, "standard");
        System.out.println(regularSummary);

        System.out.println("Premium customer standard discount:");
        OrderSummary premiumSummary = premiumProcessor.processOrder(smallCart, "standard");
        System.out.println(premiumSummary);

        // Loyalty discount
        System.out.println("Premium customer with loyalty points:");
        OrderSummary loyaltySummary = premiumProcessor.processOrderWithLoyalty(cart, 1500);
        System.out.println(loyaltySummary);
    }
}