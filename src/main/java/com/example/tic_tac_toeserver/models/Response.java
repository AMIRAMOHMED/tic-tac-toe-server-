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
            case "PlayerList" -> getPlayList(response);
            case "Scoreboard" -> getScoreBoard();
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
    private static String getPlayList(String response){
        String reply= "";
        apiFunctions api = new apiFunctions();
        ResultSet rs;
        if (response.contains("Search")) {
            JSONObject object = new JSONObject(response);
            rs = api.read("SELECT * FROM player WHERE isloggedin = 1 , username =%"+object.getString("Search")+"%");
        }
        else
            rs = api.read("SELECT * FROM player WHERE isloggedin = 1");

        try {
            reply = "{\"PlayList\":[";
            while (rs.next()) {
                reply += "{\"username\":\""+rs.getString("username")+"\" , \"isloggedin\":"+rs.getBoolean(3)+"},";
            }
            reply += "]}";
        } catch (SQLException ignored){

        }
        System.out.println(reply);
        return reply;
    }
    private static String getScoreBoard(){
        String reply= "";
        apiFunctions api = new apiFunctions();
        ResultSet rs = api.read("SELECT * FROM player ORDER BY score DESC LIMIT 20 ");

        try {
            reply = "{\"Scoreboard\":[";
            while (rs.next()) {
                reply += "{\"username\":\""+rs.getString("username")+"\" , \"score\":"+rs.getInt("score")+"},";
            }
            reply += "]}";
        } catch (SQLException ignored){

        }
        System.out.println(reply);
        return reply;
    }
}
