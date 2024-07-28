package com.example.tic_tac_toeserver.logic;//


import com.example.tic_tac_toeserver.models.Response;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    DataInputStream input;
    DataOutput output;

    public Server() {
        try {
            serverSocket = new ServerSocket(5005, 0, InetAddress.getByName("192.168.1.3"));
            System.out.println("Server started on port 5005");
            clientSocket = serverSocket.accept();
            while(true) {

                System.out.println(clientSocket.toString());
                input = new DataInputStream(clientSocket.getInputStream());
                output = new DataOutputStream(clientSocket.getOutputStream());
                String s = input.readUTF();
                System.out.println(s);
                String response = Response.getResponse(s);
                output.writeUTF(response);
            }
        } catch (IOException | SQLException var2) {
            IOException e = (IOException) var2;
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new Server();
    }
}
