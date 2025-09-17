package org.example;

public class Transaction {
    private String transactionType;
    private String accountId;
    private double amount;
    private String targetAccountId;

    public Transaction(String transactionType, String accountId, double amount) {
        this.transactionType = transactionType;
        this.accountId = accountId;
        this.amount = amount;
    }

    public Transaction(String transactionType, String accountId, double amount, String targetAccountId) {
        this(transactionType, accountId, amount);
        this.targetAccountId = targetAccountId;
    }

    public String getTransactionType() { return transactionType; }
    public String getAccountId() { return accountId; }
    public double getAmount() { return amount; }
    public String getTargetAccountId() { return targetAccountId; }
}
