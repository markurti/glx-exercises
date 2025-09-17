package org.example;

public class AccountInfo {
    private String accountId;
    private String customerName;
    private double initialBalance;

    public AccountInfo(String accountId, String customerName, double initialBalance) {
        this.accountId = accountId;
        this.customerName = customerName;
        this.initialBalance = initialBalance;
    }

    public String getAccountId() { return accountId; }
    public String getCustomerName() { return customerName; }
    public double getInitialBalance() { return initialBalance; }
}
