package org.example;

public class OrderSummary {
    private final double originalTotal;
    private final double discountAmount;
    private final double finalTotal;
    private final String discountDescription;

    public OrderSummary(double originalTotal, double discountAmount, double finalTotal, String discountDescription) {
        this.originalTotal = originalTotal;
        this.discountAmount = discountAmount;
        this.finalTotal = finalTotal;
        this.discountDescription = discountDescription;
    }

    // Getters
    public double getOriginalTotal() { return originalTotal; }
    public double getDiscountAmount() { return discountAmount; }
    public double getFinalTotal() { return finalTotal; }
    public String getDiscountDescription() { return discountDescription; }

    @Override
    public String toString() {
        return String.format("""
            
            ORDER SUMMARY
            =============
            Original Total: $%.2f
            Discount: $%.2f (%s)
            Final Total: $%.2f
            Savings: $%.2f
            """, originalTotal, discountAmount, discountDescription, finalTotal, discountAmount);
    }
}
