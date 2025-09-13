package org.example;

public interface OrderProcessor extends DiscountCalculator {
    // Default method for adding items to cart
    default void addItemToCart(Cart cart, Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        cart.getItems().add(item);
        System.out.println("Added item to cart.");
    }


}
