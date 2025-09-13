package org.example;

import java.util.ArrayList;
import java.util.List;

public interface DiscountCalculator {
    // Static method for percentage-based discount
    static double calculatePercentageDiscount(double totalPrice, double discountPercentage) {
        if (discountPercentage < 0.0 || discountPercentage > 100) {
            throw new IllegalArgumentException("Discount percentage must be between 0 and 100");
        }
        return totalPrice * (discountPercentage / 100);
    }

    // Static method for buy-one-get-one-free discount
    static double calculateBuyOneGetOneFreeDiscount(List<Item> items) {
        if (items.isEmpty()) return 0.0;

        // Sort items by price to give free items with lower value
        List<Item> sortedItems = new ArrayList<>(items);
        sortedItems.sort((a, b) -> Double.compare(b.getPrice(), a.getPrice()));

        double discount = 0.0;
        for (int i = 1; i < sortedItems.size(); i += 2) {
            discount += sortedItems.get(i).getPrice();
        }

        return discount;
    }

    // Static method for fixed amount discount
    static double calculateFixedAmountDiscount(double totalPrice, double discountAmount) {
        return Math.min(totalPrice, discountAmount); // cannot be more than total price
    }

    // Static method for tiered discount based on total amount
    static double calculateTieredDiscount(double totalPrice) {
        if (totalPrice >= 500) {
            return totalPrice * 0.20;
        } else if (totalPrice >= 200) {
            return totalPrice * 0.15;
        } else if (totalPrice >= 100) {
            return totalPrice * 0.10;
        }
        return 0.0;
    }

    // Default method that can be overridden for custom discounts
    default double applyStandardDiscount(double totalPrice, List<Item> items) {
        // Standard discount: 5% for orders over $50
        return totalPrice > 50 ? calculatePercentageDiscount(totalPrice, 5.0) : 0.0;
    }

    // Default method for applying bulk discount
    default double applyBulkDiscount(List<Item> items, double totalPrice) {
        // Bulk discount: 2% per item over 5 items, max 20%
        if (items.size() > 5) {
            double discountPercentage = Math.min((items.size() - 5) * 2.0, 20.0);
            return calculatePercentageDiscount(totalPrice, discountPercentage);
        }
        return 0.0;
    }

    // Default method for seasonal promotions
    default double applySeasonalDiscount(double totalPrice, String season) {
        return switch (season.toLowerCase()) {
            case "winter" -> calculatePercentageDiscount(totalPrice, 15.0);
            case "summer" -> calculatePercentageDiscount(totalPrice, 10.0);
            case "spring", "fall" -> calculatePercentageDiscount(totalPrice, 5.0);
            default -> 0.0;
        };
    }
}
