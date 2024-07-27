package com.example.tic_tac_toeserver.logic;

import com.example.tic_tac_toeserver.models.Player;
import com.example.tic_tac_toeserver.models.Response;
import com.example.tic_tac_toeserver.models.User;
import com.mysql.cj.xdevapi.Client;

import java.io.*;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    Player player;
    private static ArrayList<ClientHandler> clients;
    public ClientHandler(Socket clientSocket, Player player) {
        this.clientSocket = clientSocket;
        this.player = player;
        clients.add(this);
    }
    public boolean sendGameRequest(Player opponent){
        int opponentClient=clients.indexOf(opponent);
        if(opponentClient!=-1){
            System.out.println("i found opponent");
            ClientHandler opponentClientHandler=clients.get(opponentClient);
            //opponentClientHandler.clientSocket.getOutputStream("").
        }
        return true;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            String request;
            while ((request = in.readLine()) != null) {
                //Response.getResponse();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
