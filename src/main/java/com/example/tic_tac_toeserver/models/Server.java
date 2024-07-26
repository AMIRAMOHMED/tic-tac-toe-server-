package com.example.tic_tac_toeserver.models;//


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;

    public Server() {
        try {
            this.serverSocket = new ServerSocket(5005);
            System.out.println("Server started on port 5005");

            while(true) {
                Socket clientSocket = this.serverSocket.accept();
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
