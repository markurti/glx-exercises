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

    // Default method for removing items from cart
    default boolean removeItemFromCart(Cart cart, Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        boolean removed = cart.getItems().remove(item);
        if (removed) {
            System.out.println("Removed item from cart.");
        } else {
            System.out.println("Item not found in cart.");
        }
        return removed;
    }

    // Default method for calculating total price
    default double calculateTotalPrice(Cart cart) {
        return cart.getItems().stream().mapToDouble(Item::getPrice).sum();
    }

    // Defulat method
}
