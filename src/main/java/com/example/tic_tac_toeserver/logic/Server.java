package com.example.tic_tac_toeserver.logic;//


import com.example.tic_tac_toeserver.models.Response;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    DataInputStream input;
    DataOutput output;
    private boolean running = false;

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

    public void  run () {

        try {
            serverSocket = new ServerSocket(5005, 0, InetAddress.getByName("10.10.15.15"));
            System.out.println("Server started on port 5005");
            while(running) {
                clientSocket = serverSocket.accept();
                System.out.println(clientSocket.toString());
                input = new DataInputStream(clientSocket.getInputStream());
                output = new DataOutputStream(clientSocket.getOutputStream());
                String s = input.readUTF();
                System.out.println(s);
                String response = Response.getResponse(s);
                output.writeUTF(response);
            }
        } catch (IOException var2) {
            IOException e = var2;
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Server server =  new Server();
        server.startServer();
    }
}
