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

    // Defulat method for processing order with discounts
    default OrderSummary processOrder(Cart cart, String discountType, Object ... params) {
        double originalTotal = calculateTotalPrice(cart);
        double discount = 0.0;
        String discountDescription = "No discounts applied";

        if (originalTotal == 0) {
            return new OrderSummary(originalTotal, discount, originalTotal, discountDescription);
        }

        switch (discountType.toLowerCase()) {
            case "percentage":
                if (params.length > 0 && params[0] instanceof Double) {
                    discount = DiscountCalculator.calculatePercentageDiscount(originalTotal, (Double) params[0]);
                    discountDescription = String.format("%.1f%% discount", (Double) params[0]);
                }
                break;
            case "bogo":
                discount = DiscountCalculator.calculateBuyOneGetOneFreeDiscount(cart.getItems());
                discountDescription = "Buy One Get One Free";
                break;
            case "fixed":
                if (params.length > 0 && params[0] instanceof Double) {
                    discount = DiscountCalculator.calculateFixedAmountDiscount(originalTotal, (Double) params[0]);
                    discountDescription = String.format("$%.2f fixed discount", (Double) params[0]);
                }
                break;
            case "tiered":
                discount = DiscountCalculator.calculateTieredDiscount(originalTotal);
                discountDescription = "Tiered discount";
                break;
            case "standard":
                discount = applyStandardDiscount(originalTotal, cart.getItems());
                discountDescription = "Standard discount";
                break;
            case "bulk":
                discount = applyBulkDiscount(cart.getItems(), originalTotal);
                discountDescription = "Bulk discount";
                break;
            case "seasonal":
                if (params.length > 0 && params[0] instanceof String) {
                    discount = applySeasonalDiscount(originalTotal, (String) params[0]);
                    discountDescription = String.format("%s seasonal discount", (String) params[0]);
                }
                break;
        }

        double finalTotal = originalTotal - discount;
        return new OrderSummary(originalTotal, discount, finalTotal, discountDescription);
    }

    // Default method for displaying cart summary
    default void displayCartSummary(Cart cart) {
        System.out.println("\n" + cart);
        double total = calculateTotalPrice(cart);
        System.out.printf("Total: $%.2f\n", total);
        System.out.printf("Items in cart: %d\n", cart.getItemCount());
    }
}
