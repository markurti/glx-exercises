package org.example;

public class BankingFacade {
    private AccountManager accountManager;
    private TransactionProcessor transactionProcessor;
    private String currentAccountId;

    public BankingFacade() {
        this.accountManager = new AccountManager();
        this.transactionProcessor = new TransactionProcessor(accountManager);
        this.currentAccountId = "ACC001"; // Default account for simple operations

        // Create a default account for demo purposes
        AccountInfo defaultAccount = new AccountInfo(currentAccountId, "John Doe", 1000.0);
        accountManager.createAccount(defaultAccount);

        // Create additional accounts for transfer demo
        AccountInfo account2 = new AccountInfo("ACC002", "Jane Smith", 500.0);
        accountManager.createAccount(account2);
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Error: Deposit amount must be positive");
            return;
        }

        Transaction transaction = new Transaction("DEPOSIT", currentAccountId, amount);
        transactionProcessor.processTransaction(transaction);
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Error: Withdrawal amount must be positive");
            return;
        }

        Transaction transaction = new Transaction("WITHDRAWAL", currentAccountId, amount);
        transactionProcessor.processTransaction(transaction);
    }

    public void transfer(String fromAccount, String toAccount, double amount) {
        if (amount <= 0) {
            System.out.println("Error: Transfer amount must be positive");
            return;
        }

        Transaction transaction = new Transaction("TRANSFER", fromAccount, amount, toAccount);
        transactionProcessor.processTransaction(transaction);
    }

    public double getBalance(String accountId) {
        if (!accountManager.accountExists(accountId)) {
            System.out.println("Error: Account not found - " + accountId);
            return -1;
        }

        return accountManager.getBalance(accountId);
    }

    public void setCurrentAccount(String accountId) {
        if (accountManager.accountExists(accountId)) {
            this.currentAccountId = accountId;
            System.out.println("Switched to account: " + accountId);
        } else {
            System.out.println("Error: Account not found - " + accountId);
        }
    }
}
