package org.example;

public class User extends Participant {
    public User(ChatMediator mediator, String name) {
        super(mediator, name);
    }

    @Override
    public void send(String message) {
        System.out.println("[" + name + "] sends: " + message);
        mediator.sendMessage(message, this);
    }

    @Override
    public void receive(String message, String from) {
        System.out.println("[" + name + "] receives from " + from + ": " + message);
    }
}
