package com.example.tic_tac_toeserver.models;

import com.example.tic_tac_toeserver.constants.RequestType;
import com.example.tic_tac_toeserver.database.apiFunctions;
import com.example.tic_tac_toeserver.logic.GameHandler;
import com.example.tic_tac_toeserver.logic.PlayerHandler;
import com.example.tic_tac_toeserver.logic.Server;
import com.example.tic_tac_toeserver.logic.UserHandler;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;




public class Response {
    public static String getResponse(String response, UserHandler userHandler){
        JSONObject object = new JSONObject(response);
        RequestType type = RequestType.valueOf(object.getString("RequestType"));
        return switch (type) {
            case Register -> Register(response)+"";
            case Login -> Login(response, userHandler);
            case RequestGame -> RequestGame(response)+"";
//            case PlayAgain -> "";
//            case Surrender -> "";
            case PlayerList -> getPlayList();
            case Scoreboard -> getScoreBoard();
//            case GameHistory -> "";
            default -> "";
        };
    }
    private static String Login(String json, UserHandler userHandler){
        JSONObject object = new JSONObject(json);
        User user = User.toUser(object.getJSONObject("User"));
        apiFunctions api = new apiFunctions();
        ResultSet rs = api.read("SELECT * FROM user WHERE username = \""+user.getUsername()+"\" AND password = \""+user.getPassword()+"\"");
        try {
            if (rs.next()) {
                ResultSet fs = api.read("SELECT * FROM player WHERE userid= "+rs.getInt("userid"));
                if (fs.next()) {
                    userHandler.setPlayer(new Player(fs.getInt("userid"), fs.getString("username"), fs.getBoolean("isloggedin"), fs.getBoolean("isingame"), fs.getInt("gamesplayed"), fs.getInt("wins"), fs.getInt("draws"), fs.getInt("losses")));
                    Server.clients.put(fs.getInt("userid"),userHandler);
                }
                    return "{\"userid\":"+userHandler.getPlayer().getUserid()+"}";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "{\"invalid\":\"Invalid username or password\"}";
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
            reply = "{\"PlayList\":[";
            while (rs.next()) {
                reply += "{\"username\":\""+rs.getString("username")+"\" , \"isingame\":"+rs.getBoolean("isingame")+",\"userid\":"+rs.getInt("userid")+"},";
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
    private static boolean RequestGame(String response){
        JSONObject object = new JSONObject(response);
        int userid = object.getInt("userid");
        int opponentid = object.getInt("opponentid");
        String sender = Server.clients.get(userid).getPlayer().toString();
        Server.clients.get(opponentid).send("{\"RequestType\":\"RequestGame\"+\"Player\":"+sender+"}");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(Server.clients.get(opponentid).getUserSocket().getInputStream()));
            JSONObject answer = new JSONObject(reader.readLine());
            return answer.getBoolean("Reply");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean Register(String response){
        return false;
    }
}
