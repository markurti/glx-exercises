package org.example;

public class Main {
    public static void main(String[] args) {
        int port = 1234;
        Server server = new Server(port);
        server.startServer();

        Client client1 = new Client("Client1", "localhost", port);
        Client client2 = new Client("Client2", "localhost", port);

        client1.start(new String[]{"Hello from Client1!", "Hey", "This is a message!"});
        client2.start(new String[]{"Hello from Client2!", "Bye!", "This is another message!"});
    }
}