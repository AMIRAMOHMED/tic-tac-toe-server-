package com.example.tic_tac_toeserver.logic;
import com.example.tic_tac_toeserver.models.Player;

import java.net.Socket;

public class PlayerHandler{
    private UserHandler userHandler;
    private Player player;



    public PlayerHandler(Socket userSocket, Player player) {
        this.userHandler = new UserHandler(userSocket);
        this.player = player;
    }
    public Player getPlayer() {
        return player;
    }
    public UserHandler getUserHandler() {
        return userHandler;
    }
    public void sendData(Object object) { userHandler.send(object); }
}
