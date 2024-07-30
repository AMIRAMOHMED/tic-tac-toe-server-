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
    boolean running = false;
static{
    clients = new HashMap<>();
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
}