package com.example.tic_tac_toeserver.logic;//


import com.example.tic_tac_toeserver.models.Player;
import com.example.tic_tac_toeserver.models.Response;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    ClientHandler user;
    ClientHandler opponent;
    DataInputStream input;
    DataOutput output;

    public Server() {
        try {
            serverSocket = new ServerSocket(5005, 0, InetAddress.getByName("10.10.13.101"));
            System.out.println("Server started on port 5005");
            while(true) {
                clientSocket = serverSocket.accept();
//                user = new ClientHandler(clientSocket,new Player("filo",true,true,0,0,0,0,0));
//            System.out.println("user connected!!");
//                clientSocket = serverSocket.accept();
//                opponent = new ClientHandler(clientSocket,new Player("amira",true,true,0,0,0,0,0));
//            System.out.println("opponent connected!!");
//                GameHandler game = new GameHandler(user,opponent);
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
        new Server();
    }
}
