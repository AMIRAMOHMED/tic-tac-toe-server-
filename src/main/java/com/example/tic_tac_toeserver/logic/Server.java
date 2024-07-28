package com.example.tic_tac_toeserver.logic;

import com.example.tic_tac_toeserver.models.Response;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class Server extends Thread {
    private ServerSocket serverSocket;
    private ClientHandler clientSocket;
    private boolean running = false;
    private int userId;
    private String username;

    public void startServer() {
        if (!running) {
            running = true;
            this.start();
        }
    }

    public void stopServer() {
        running = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(5005, 0, InetAddress.getByName("192.168.1.3"));
            System.out.println("Server started on port 5005");
            while (running) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.toString());
                // Spawn a new thread to handle the client
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.startServer();
    }

    /*private static class ClientHandler extends Thread {
        private final Socket clientSocket;
        private DataInputStream input;
        private DataOutputStream output;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {

            try {
                input = new DataInputStream(clientSocket.getInputStream());
                output = new DataOutputStream(clientSocket.getOutputStream());
                String initialMessage = input.readUTF();
                JSONObject jsonObject = new JSONObject(initialMessage);



                while (true) {
                    String message = input.readUTF();
                    System.out.println("Received: " + message);
                    String response = Response.getResponse(message);
                    output.writeUTF(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
    private String getUsernameFromSession() {
        return username;
    }*/
}