package com.example.tic_tac_toeserver.models;

import com.example.tic_tac_toeserver.database.apiFunctions;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

import static sun.net.www.protocol.http.AuthCacheValue.Type.Server;


public class Response {
    public static String getResponse(String response){
        JSONObject object = new JSONObject(response);
        String type = object.getString("RequestType");

        return switch (type) {
            case "Register" -> Register(object);
            case "Login" -> Login(object);
            case "RequestGame" -> RequestGame(object);
            case "PlayAgain" -> "";
            case "Surrender" -> "";
            case "PlayerList" -> getPlayList();
            case "Scoreboard" -> "";
            case "GameHistory" -> "";
            default -> "";
        };

    }
    private static String RequestGame(JSONObject json) {
        int playerId = json.getInt("PlayerId");
        int targetId = json.getInt("TargetId");

        apiFunctions api = new apiFunctions();
        String playerUsername = api.getUsernameById(playerId);
        String targetUsername = api.getUsernameById(targetId);

        if (targetUsername != null && playerUsername != null) {
            String message = playerUsername + " wants to play with you!";
            Server.sendMessageToPlayer(targetId, message);
            return "Request sent to " + targetUsername;
        } else {
            return "Player not found";
        }
    }



    private static String Login(JSONObject json) {
        String reply = "";
        apiFunctions api = new apiFunctions();
        JSONObject object = json.getJSONObject("User");
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
                reply = "{\"PlayList\":[";
                while (rs.next()) {
                    reply += "{\"userid\":"+rs.getInt("userid")+", \"username\":\""+rs.getString("username")+"\" , \"isingame\":"+rs.getBoolean("isingame")+"},";
                }
                reply += "]}";
            }
        } catch (SQLException ignored){

        }
        return reply;
    }
}
