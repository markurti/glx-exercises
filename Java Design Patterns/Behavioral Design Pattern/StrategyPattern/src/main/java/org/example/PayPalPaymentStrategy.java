package org.example;

public class PayPalPaymentStrategy implements PaymentStrategy {
    private String email;

    public PayPalPaymentStrategy(String email) {
        this.email = email;
    }

    @Override
    public void processPayment(double amount) {
        System.out.println("Processing PayPal Payment");
        System.out.println("Amount: $" + amount);
        System.out.println("PayPal Account: " + email);
        System.out.println("PayPal payment of $" + amount + " processed successfully!");
    }
}
