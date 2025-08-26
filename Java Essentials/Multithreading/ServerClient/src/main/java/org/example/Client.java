package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private final String name;
    private final String host;
    private final int port;

    public Client(String name, String host, int port) {
        this.name = name;
        this.host = host;
        this.port = port;
    }

    public void start(String[] messages) {
        try (
            Socket socket = new Socket(host, port);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
        ) {
            for (String msg : messages) {
                output.println(msg);
                String response = input.readLine();
                System.out.println(this.name + " received: " + response);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
