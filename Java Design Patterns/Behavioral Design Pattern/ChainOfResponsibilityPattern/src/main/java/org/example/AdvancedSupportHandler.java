package org.example;

public class AdvancedSupportHandler implements SupportHandler {
    private SupportHandler nextHandler;

    public void setNextHandler(SupportHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(SupportTicket ticket) {
        if (ticket.getComplexity().equals("Advanced")) {
            System.out.println("Advanced Support Handler: Resolving ticket - " + ticket.getDescription());
        } else {
            System.out.println("Advanced Support Handler: Unable to resolve ticket - " + ticket.getDescription());
        }
    }
}
