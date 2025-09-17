package org.example;

public class Client {
    public static void main(String[] args) {
        System.out.println("Facade Pattern Demo - Banking System");
        System.out.println("===================================");

        // Create banking facade - hides complexity of subsystems
        BankingFacade bankingFacade = new BankingFacade();

        // Simple banking operations through facade
        System.out.println("\n1. Check initial balance:");
        double balance = bankingFacade.getBalance("ACC001");
        System.out.println("Account ACC001 balance: $" + balance);

        System.out.println("\n2. Deposit money:");
        bankingFacade.deposit(200.0);

        System.out.println("\n3. Check balance after deposit:");
        balance = bankingFacade.getBalance("ACC001");
        System.out.println("Account ACC001 balance: $" + balance);

        System.out.println("\n4. Withdraw money:");
        bankingFacade.withdraw(150.0);

        System.out.println("\n5. Transfer money between accounts:");
        bankingFacade.transfer("ACC001", "ACC002", 300.0);

        System.out.println("\n6. Check final balances:");
        balance = bankingFacade.getBalance("ACC001");
        System.out.println("Account ACC001 balance: $" + balance);
        balance = bankingFacade.getBalance("ACC002");
        System.out.println("Account ACC002 balance: $" + balance);

        System.out.println("\n7. Demonstrate error handling:");
        bankingFacade.withdraw(2000.0); // Should fail - insufficient funds
        bankingFacade.getBalance("INVALID"); // Should fail - account not found
    }
}