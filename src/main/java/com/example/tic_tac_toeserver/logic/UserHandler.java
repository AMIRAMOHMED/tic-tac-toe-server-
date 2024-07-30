package com.example.tic_tac_toeserver.logic;

import com.example.tic_tac_toeserver.models.Player;
import com.example.tic_tac_toeserver.models.Response;
import java.io.*;
import java.net.Socket;

public class UserHandler extends Thread {
    Socket userSocket;
    private Player player;
    BufferedReader input;
    PrintWriter output;
    public UserHandler(Socket userSocket){
        this.userSocket =  userSocket;
        try {
            input = new BufferedReader(new InputStreamReader(userSocket.getInputStream()));
            output = new PrintWriter(userSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        start();
    }

    public Socket getUserSocket() {
        return userSocket;
    }

    @Override
    public void run() {
        listen();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void listen() {
        new Thread(() -> {
            try {
                String line = "";
                while ((line = input.readLine()) != null) {
                    System.out.println(line);
                    send(Response.getResponse(line,this));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Server.clients.remove(this);
            disconnect();
        }).start();
    }
    public void send(Object data) {
        output.println(data);
        output.flush();
    }


    public void disconnect() {
        try {
            closeReader();
            closeSender();
            userSocket.close();
            System.out.println("Disconnected, successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Disconnected, with failure.");
        }
    }

    private void closeReader() {
        try {
         input.close();
    } catch (Exception e) {
        }
        }

    private void closeSender() {
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
