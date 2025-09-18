package org.example;

public class IntermediateSupportHandler implements SupportHandler {
    private SupportHandler nextHandler;

    public void setNextHandler(SupportHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(SupportTicket ticket) {
        if (ticket.getComplexity().equals("Intermediate")) {
            System.out.println("Intermediate Support Handler: Resolving ticket - " + ticket.getDescription());
        } else if (nextHandler != null) {
            System.out.println("Intermediate Support Handler: Passing to next level");
            nextHandler.handleRequest(ticket);
        }
    }
}
