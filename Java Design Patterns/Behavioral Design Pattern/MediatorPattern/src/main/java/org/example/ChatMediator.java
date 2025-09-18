package org.example;

public interface ChatMediator {
    void sendMessage(String message, Participant sender);
    void addUser(Participant participant);
}
