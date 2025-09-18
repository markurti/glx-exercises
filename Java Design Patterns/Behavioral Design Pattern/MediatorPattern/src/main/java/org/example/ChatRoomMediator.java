package org.example;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomMediator implements ChatMediator {
    private List<Participant> participants = new ArrayList<>();

    @Override
    public void addUser(Participant participant) {
        participants.add(participant);
        System.out.println(participant.getName() + " joined the chat room");
    }

    @Override
    public void sendMessage(String message, Participant sender) {
        for (Participant participant : participants) {
            if (participant != sender) {
                participant.receive(message, sender.getName());
            }
        }
    }

    public void sendPrivateMessage(String message, Participant sender, Participant receiver) {
        System.out.println("[" + sender.getName() + "] sends private message to " + receiver.getName() + ": " + message);
        receiver.receive(message + " (private)", sender.getName());
    }
}
