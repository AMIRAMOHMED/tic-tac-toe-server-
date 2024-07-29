package com.example.tic_tac_toeserver.logic;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;


public class Server extends Thread {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    public static HashMap<Integer, UserHandler> clients;
    public static HashMap<String, GameHandler> games ;
    boolean running = false;
static{
    clients = new HashMap<>();
    games =new HashMap<>();
}

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
            serverSocket = new ServerSocket(5005);
            System.out.println("Server started on port 5005");
            while(true) {
                clientSocket = serverSocket.accept();
                new UserHandler(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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