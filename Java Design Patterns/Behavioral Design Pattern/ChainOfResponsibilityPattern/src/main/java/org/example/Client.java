package org.example;

public class Client {
    public static void main(String[] args) {
        // Create handlers
        BasicSupportHandler basicHandler = new BasicSupportHandler();
        IntermediateSupportHandler intermediateHandler = new IntermediateSupportHandler();
        AdvancedSupportHandler advancedHandler = new AdvancedSupportHandler();

        // Set up chain
        basicHandler.setNextHandler(intermediateHandler);
        intermediateHandler.setNextHandler(advancedHandler);

        // Create tickets with varying complexity
        SupportTicket ticket1 = new SupportTicket("Basic", "Password reset request");
        SupportTicket ticket2 = new SupportTicket("Intermediate", "Database connection issues");
        SupportTicket ticket3 = new SupportTicket("Advanced", "System architecture optimization");

        System.out.println("=== Processing Support Tickets ===");

        System.out.println("\nTicket 1:");
        basicHandler.handleRequest(ticket1);

        System.out.println("\nTicket 2:");
        basicHandler.handleRequest(ticket2);

        System.out.println("\nTicket 3:");
        basicHandler.handleRequest(ticket3);
    }
}