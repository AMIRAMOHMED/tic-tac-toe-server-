package com.example.tic_tac_toeserver.logic;//


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
static{
    clients = new HashMap<>();
    games =new HashMap<>();
}
    public Server() {
        try {
            serverSocket = new ServerSocket(5005,0, InetAddress.getByName(""));
            System.out.println("Server started on port 5005");
            while(true) {
                clientSocket = serverSocket.accept();
                new UserHandler(clientSocket);
            }
        } catch (IOException var2) {
            IOException e = var2;
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}
