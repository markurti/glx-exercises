package org.example;

public class SupportTicket {
    private String complexity;
    private String description;

    public SupportTicket(String complexity, String description) {
        this.complexity = complexity;
        this.description = description;
    }

    public String getComplexity() {
        return complexity;
    }

    public String getDescription() {
        return description;
    }
}
