package com.example.tic_tac_toeserver.models;

import com.example.tic_tac_toeserver.constants.RequestType;
import com.example.tic_tac_toeserver.logic.ClientHandler;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.Socket;

public class Response {
    static ClientHandler clientHandler;
    public static String getResponse(String response , Socket clientSocket){
        JSONObject object = new JSONObject(response);
        String type = object.getString("RequestType");
        if (type.equals("GameRequest")){
            Player player = new Player();
            player.fromJson(object.getString("Player"));
            clientHandler =new ClientHandler(clientSocket,player);
            return player.getUsername();
        }
        return "";
//        switch (type) {
//            case "Register":
//                return "";
//            case "Login":
//                return "";
//            case "RequestGame":
//                Player player = new Player();
//                player.fromJson(object.getString("Player"));
//                return player.getUsername();
//
//            case "PlayAgain":
//                return "";
//
//            case "Surrender":
//                return "";
//
//            case "PlayerList":
//                return "";
//
//            case "Scoreboard":
//                return "";
//
//            case "GameHistory":
//                return "";
//
//            default:
//                return "";
//
//        }
            }
    private void Login(String json){
        
    }
}
