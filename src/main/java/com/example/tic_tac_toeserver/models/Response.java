package com.example.tic_tac_toeserver.models;

import com.example.tic_tac_toeserver.database.apiFunctions;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Response {
    public static String getResponse(String response){
        JSONObject object = new JSONObject(response);
        String type = object.getString("RequestType");
        return switch (type) {
            case "Register" -> "";
            case "Login" -> "";
            case "RequestGame" -> "";
            case "PlayAgain" -> "";
            case "Surrender" -> "";
            case "PlayerList" -> getPlayList();
            case "Scoreboard" -> "";
            case "GameHistory" -> "";
            default -> "";
        };
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
    private static String getPlayList(){
        String reply= "";
        apiFunctions api = new apiFunctions();
        ResultSet rs = api.read("SELECT * FROM player WHERE isloggedin = 1");
        try {
            while (rs.next()) {
                Player player = new Player();
                player.fromJson(rs.toString());
                reply += player.toString();
            }
        } catch (SQLException ignored){

        }
        return reply;
    }
}
