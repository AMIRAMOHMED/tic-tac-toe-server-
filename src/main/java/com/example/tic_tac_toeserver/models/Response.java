package com.example.tic_tac_toeserver.models;

import com.example.tic_tac_toeserver.constants.RequestType;
import org.json.JSONObject;

public class Response {
    public static String getResponse(String response){
        JSONObject object = new JSONObject(response);
        String type = object.getString("RequestType");
        if (type.equals("GameRequest")){
            Player player = new Player();
                player.fromJson(object.getString("Player"));
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
