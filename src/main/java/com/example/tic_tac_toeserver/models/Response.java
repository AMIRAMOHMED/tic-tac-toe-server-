package com.example.tic_tac_toeserver.models;

import com.example.tic_tac_toeserver.database.apiFunctions;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Response {
    public static String getResponse(String response){
        JSONObject object = new JSONObject(response);
        String type = object.getString("RequestType");

        return switch (type) {
            case "Register" -> Register(object);
            case "Login" -> Login(object);
            case "RequestGame" -> "";
            case "PlayAgain" -> "";
            case "Surrender" -> "";
            case "PlayerList" -> getPlayList();
            case "Scoreboard" -> "";
            case "GameHistory" -> "";
            default -> "";
        };

    }


    private static String Login(JSONObject json) {
        String reply = "";
        apiFunctions api = new apiFunctions();
        JSONObject object = new JSONObject(json.getString("User"));
        String username = object.getString("username");
        String password = object.getString("password");

        ResultSet rs = api.read("SELECT * FROM user WHERE username = '" + username + "' AND password = '" + password + "'");
        try {
            if (rs.next()) {
                // Update player's login status
                int updateStatus = api.update("UPDATE player SET isloggedin = 1 WHERE username = '" + username + "'");
                if (updateStatus > 0) {
                    reply = "Success";
                } else {
                    reply = "Failed to update login status";
                }
            } else {
                reply = "Invalid username or password";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            reply = "Error occurred during login";
        }

        return reply;
    }
    private static String Register(JSONObject jsonObject) {
        String reply= "";
        apiFunctions api = new apiFunctions();
        JSONObject object = jsonObject.getJSONObject("User");
        String username = object.getString("username");
        String email = object.getString("email");
        String password = object.getString("password");

        int rsInsertPlayer = api.create("INSERT INTO player (username, isloggedin,isingame, score, wins, draws, losses) VALUES ('"+username+"', 0,0,0,0,0,0)");
        int rsInsertUser = api.create("INSERT INTO user (username,email, password) VALUES ('"+username+"', '"+email+"', '"+password+"')");
        return ""+rsInsertPlayer+rsInsertUser;
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
