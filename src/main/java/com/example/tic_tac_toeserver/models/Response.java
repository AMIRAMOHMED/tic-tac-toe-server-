package com.example.tic_tac_toeserver.models;

import com.example.tic_tac_toeserver.database.DatabaseConnection;
import com.example.tic_tac_toeserver.database.apiFunctions;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Response {
    public static String getResponse(String response) throws SQLException {
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


    public static String Login(JSONObject request) {
        JSONObject userJson = request.getJSONObject("User");
        User user = new User();
        user.fromJson(userJson.toString());

        DatabaseConnection db = new DatabaseConnection();
        String query = "SELECT * FROM user WHERE username='" + user.getUsername() + "' AND password='" + user.getPassword() + "'";

        System.out.println("Executing query: " + query); // Debug: Print the query

        ResultSet rs = db.getData(query);

        try {
            if (rs != null && rs.next()) {
                return "1"; // Login success
            } else {
                return "0"; // Login failed
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "0";
        } finally {
            db.closeConnection();
        }
    }

    /*private static String Login(JSONObject json) throws SQLException {
        String reply= "";
        apiFunctions api = new apiFunctions();
        JSONObject object = json.getJSONObject("User");
        String username = object.getString("username");
        String password = object.getString("password");

        ResultSet rs = api.read("SELECT * FROM user WHERE username = '"+username+"'AND password='"+password+"'");
        if(rs.next()){
            User user = new User();
            user.fromJson(rs.toString());
            reply += user.toString();
            return "1";
        } else{
            return"0";
        }
    }*/
    private static String Register(JSONObject jsonObject) {
        String reply= "";
        apiFunctions api = new apiFunctions();
        JSONObject object = jsonObject.getJSONObject("User");
        String username = object.getString("username");
        String email = object.getString("email");
        String password = object.getString("password");

        int rsInsertPlayer = api.create("INSERT INTO player (username, isloggedin,isingame, score, wins, draws, losses) VALUES ('"+username+"', 0,0,0,0,0,0)");
        int rsInsertUser = api.create("INSERT INTO user (username,email, password) VALUES ('"+username+"', '"+email+"', '"+password+"')");
        return ""+rsInsertUser;
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
