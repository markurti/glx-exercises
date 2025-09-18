package org.example;

public class Client {
    public static void main(String[] args) {
        PaymentProcessor processor = new PaymentProcessor();

        System.out.println("=== Payment Processing Strategy Pattern Demo ===");

        // Test different payment strategies
        System.out.println("\n--- Credit Card Payment ---");
        processor.setPaymentStrategy(new CreditCardPaymentStrategy("1234567890123456", "John Doe"));
        processor.processPayment(150.75);

        System.out.println("\n--- PayPal Payment ---");
        processor.setPaymentStrategy(new PayPalPaymentStrategy("john.doe@email.com"));
        processor.processPayment(89.99);

        System.out.println("\n--- Bank Transfer Payment ---");
        processor.setPaymentStrategy(new BankTransferPaymentStrategy("9876543210", "First National Bank"));
        processor.processPayment(300.00);

        System.out.println("\n--- Testing Error Case ---");
        PaymentProcessor newProcessor = new PaymentProcessor();
        newProcessor.processPayment(50.00); // No strategy set

        System.out.println("\n--- Multiple Payments with Same Strategy ---");
        processor.setPaymentStrategy(new CreditCardPaymentStrategy("5555444433332222", "Jane Smith"));
        processor.processPayment(25.50);
        processor.processPayment(67.30);
    }
}