package org.example;

public class CreditCardPaymentStrategy implements PaymentStrategy {
    private String cardNumber;
    private String cardHolder;

    public CreditCardPaymentStrategy(String cardNumber, String cardHolder) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
    }

    @Override
    public void processPayment(double amount) {
        System.out.println("Processing Credit Card Payment");
        System.out.println("Amount: $" + amount);
        System.out.println("Card: ****-****-****-" + cardNumber.substring(cardNumber.length() - 4));
        System.out.println("Cardholder: " + cardHolder);
        System.out.println("Credit Card payment of $" + amount + " processed successfully!");
    }
}
