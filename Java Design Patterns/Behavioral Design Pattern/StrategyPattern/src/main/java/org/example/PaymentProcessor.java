package org.example;

public class PaymentProcessor {
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.paymentStrategy = strategy;
        System.out.println("Payment method updated");
    }

    public void processPayment(double amount) {
        if (paymentStrategy == null) {
            System.out.println("Error: No payment method selected!");
            return;
        }

        System.out.println("\n" + "=".repeat(40));
        paymentStrategy.processPayment(amount);
        System.out.println("=".repeat(40));
    }
}
