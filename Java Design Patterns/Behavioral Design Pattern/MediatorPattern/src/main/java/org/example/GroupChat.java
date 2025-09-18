package org.example;

import java.util.ArrayList;
import java.util.List;

public class GroupChat extends Participant {
    private List<Participant> members = new ArrayList<>();

    public GroupChat(ChatMediator mediator, String name) {
        super(mediator, name);
    }

    public void addMember(Participant participant) {
        members.add(participant);
        System.out.println(participant.getName() + " joined group: " + name);
    }

    @Override
    public void send(String message) {
        System.out.println("[Group " + name + "] broadcasts: " + message);
        for (Participant member : members) {
            member.receive(message, "Group " + name);
        }
    }

    @Override
    public void receive(String message, String from) {
        System.out.println("[Group " + name + "] receives from " + from + ": " + message);
        // Broadcast to all members
        for (Participant member : members) {
            if (!member.getName().equals(from)) {
                member.receive(message, from + " (via " + name + ")");
            }
        }
    }
}
