package org.example;

public abstract class Participant {
    protected ChatMediator mediator;
    protected String name;

    public Participant(ChatMediator mediator, String name) {
        this.mediator = mediator;
        this.name = name;
    }

    public abstract void send(String message);
    public abstract void receive(String message, String from);

    public String getName() {
        return name;
    }
}
