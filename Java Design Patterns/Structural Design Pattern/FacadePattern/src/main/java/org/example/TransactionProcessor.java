package org.example;

public class TransactionProcessor {
    private AccountManager accountManager;

    public TransactionProcessor(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public void processTransaction(Transaction transaction) {
        String type = transaction.getTransactionType();
        String accountId = transaction.getAccountId();
        double amount = transaction.getAmount();

        switch (type) {
            case "DEPOSIT":
                processDeposit(accountId, amount);
                break;
            case "WITHDRAWAL":
                processWithdrawal(accountId, amount);
                break;
            case "TRANSFER":
                processTransfer(accountId, transaction.getTargetAccountId(), amount);
                break;
            default:
                System.out.println("Unknown transaction type: " + type);
        }
    }

    private void processDeposit(String accountId, double amount) {
        if (!accountManager.accountExists(accountId)) {
            System.out.println("Error: Account not found - " + accountId);
            return;
        }

        double currentBalance = accountManager.getBalance(accountId);
        double newBalance = currentBalance + amount;
        accountManager.updateBalance(accountId, newBalance);

        System.out.println("Deposit processed: $" + amount + " to account " + accountId);
        System.out.println("New balance: $" + newBalance);
    }

    private void processWithdrawal(String accountId, double amount) {
        if (!accountManager.accountExists(accountId)) {
            System.out.println("Error: Account not found - " + accountId);
            return;
        }

        double currentBalance = accountManager.getBalance(accountId);
        if (currentBalance < amount) {
            System.out.println("Error: Insufficient funds in account " + accountId);
            return;
        }

        double newBalance = currentBalance - amount;
        accountManager.updateBalance(accountId, newBalance);

        System.out.println("Withdrawal processed: $" + amount + " from account " + accountId);
        System.out.println("New balance: $" + newBalance);
    }

    private void processTransfer(String fromAccount, String toAccount, double amount) {
        if (!accountManager.accountExists(fromAccount) || !accountManager.accountExists(toAccount)) {
            System.out.println("Error: One or both accounts not found");
            return;
        }

        double fromBalance = accountManager.getBalance(fromAccount);
        if (fromBalance < amount) {
            System.out.println("Error: Insufficient funds for transfer");
            return;
        }

        // Debit from source account
        accountManager.updateBalance(fromAccount, fromBalance - amount);

        // Credit to target account
        double toBalance = accountManager.getBalance(toAccount);
        accountManager.updateBalance(toAccount, toBalance + amount);

        System.out.println("Transfer processed: $" + amount + " from " + fromAccount + " to " + toAccount);
    }
}
