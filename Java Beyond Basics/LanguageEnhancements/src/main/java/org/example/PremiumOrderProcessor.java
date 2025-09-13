package org.example;

import java.util.List;

public class PremiumOrderProcessor implements OrderProcessor {
    // Override standard discount for premium customers
    @Override
    public double applyStandardDiscount(double totalPrice, List<Item> items) {
        // Premium customers get 10% instead of 5% discount
        return totalPrice > 25 ? DiscountCalculator.calculatePercentageDiscount(totalPrice, 10.0) : 0.0;
    }

    // Custom method for loyalty discount
    public double applyLoyaltyDiscount(double totalPrice, int loyaltyPoints) {
        // Convert loyalty points to discount: 100 points = 1% discount, max 25%
        double discountPercentage = Math.min(loyaltyPoints / 100.0, 25.0);
        return DiscountCalculator.calculatePercentageDiscount(totalPrice, discountPercentage);
    }

    // Process order with loyalty points
    public OrderSummary processOrderWithLoyalty(Cart cart, int loyaltyPoints) {
        double originalTotal = calculateTotalPrice(cart);
        double discount = applyLoyaltyDiscount(originalTotal, loyaltyPoints);
        double finalTotal = originalTotal - discount;
        String description = String.format("Loyalty discount (%d points)", loyaltyPoints);

        return new OrderSummary(originalTotal, discount, finalTotal, description);
    }
}
