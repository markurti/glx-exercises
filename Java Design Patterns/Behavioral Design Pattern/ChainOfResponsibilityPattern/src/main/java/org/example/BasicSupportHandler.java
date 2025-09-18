package org.example;

public class BasicSupportHandler implements SupportHandler {
    private SupportHandler nextHandler;

    public void setNextHandler(SupportHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(SupportTicket ticket) {
        if (ticket.getComplexity().equals("Basic")) {
            System.out.println("Basic Support Handler: Resolving ticket - " + ticket.getDescription());
        } else if (nextHandler != null) {
            System.out.println("Basic Support Handler: Passing to next level");
            nextHandler.handleRequest(ticket);
        }
    }
}
