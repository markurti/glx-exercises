package org.example;

public class BankTransferPaymentStrategy implements PaymentStrategy {
    private String bankAccount;
    private String bankName;

    public BankTransferPaymentStrategy(String bankAccount, String bankName) {
        this.bankAccount = bankAccount;
        this.bankName = bankName;
    }

    @Override
    public void processPayment(double amount) {
        System.out.println("Processing Bank Transfer Payment");
        System.out.println("Amount: $" + amount);
        System.out.println("Bank: " + bankName);
        System.out.println("Account: ****-****-" + bankAccount.substring(bankAccount.length() - 4));
        System.out.println("Bank Transfer payment of $" + amount + " processed successfully!");
    }
}
