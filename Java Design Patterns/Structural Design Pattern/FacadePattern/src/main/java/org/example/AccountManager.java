package org.example;

import java.util.HashMap;
import java.util.Map;

public class AccountManager {
    private Map<String, AccountInfo> accounts = new HashMap<>();
    private Map<String, Double> balances = new HashMap<>();

    public void createAccount(AccountInfo accountInfo) {
        accounts.put(accountInfo.getAccountId(), accountInfo);
        balances.put(accountInfo.getAccountId(), accountInfo.getInitialBalance());
        System.out.println("Account created: " + accountInfo.getAccountId() +
                " for " + accountInfo.getCustomerName());
    }

    public void deleteAccount(String accountId) {
        accounts.remove(accountId);
        balances.remove(accountId);
        System.out.println("Account deleted: " + accountId);
    }

    public boolean accountExists(String accountId) {
        return accounts.containsKey(accountId);
    }

    public double getBalance(String accountId) {
        return balances.getOrDefault(accountId, 0.0);
    }

    public void updateBalance(String accountId, double newBalance) {
        if (accountExists(accountId)) {
            balances.put(accountId, newBalance);
        }
    }
}
