package org.example;

public class Client {
    public static void main(String[] args) {
        ChatRoomMediator chatRoom = new ChatRoomMediator();

        System.out.println("=== Chat Room Demo ===");

        // Create users
        User alice = new User(chatRoom, "Alice");
        User bob = new User(chatRoom, "Bob");
        User charlie = new User(chatRoom, "Charlie");

        // Add users to chat room
        chatRoom.addUser(alice);
        chatRoom.addUser(bob);
        chatRoom.addUser(charlie);

        System.out.println("\n=== Public Messages ===");
        alice.send("Hello everyone!");
        bob.send("Hi Alice!");
        charlie.send("Good morning all!");

        System.out.println("\n=== Group Chat Demo ===");
        GroupChat devTeam = new GroupChat(chatRoom, "Dev Team");
        chatRoom.addUser(devTeam);

        devTeam.addMember(alice);
        devTeam.addMember(bob);

        devTeam.send("Team meeting at 3 PM");

        System.out.println("\n=== Private Message Demo ===");
        chatRoom.sendPrivateMessage("Can you review my code?", alice, bob);
    }
}