package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final int port;

    public Server(int port) {
        this.port = port;
    }

    public void startServer() {
        // Start server on a separate thread
        Thread serverThread = new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println("Server started on port " + port);

                // Listen for clients indefinitely
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connected: " + clientSocket);

                    // Create thread to handle client
                    Thread clientThread = new Thread(new ClientHandler(clientSocket));
                    clientThread.start();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        serverThread.start();
    }

    private record ClientHandler(Socket clientSocket) implements Runnable {
        // Receive message and echo it back to the client
        @Override
            public void run() {
                try (
                        BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
                ) {
                    String message;
                    while ((message = input.readLine()) != null) {
                        System.out.println("Received: " + message);
                        output.println("Echo: " + message);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
}
