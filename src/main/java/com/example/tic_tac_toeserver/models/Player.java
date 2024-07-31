package com.example.tic_tac_toeserver.models;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Player {
    private int userid;
    private String username;
    private boolean isloggedin;
    private boolean isingame;
    private int gamesplayed;
    private int wins;
    private int draws;
    private int losses;
    private int score;
    public Player() {
    }
    public Player(int userid, String username, boolean isloggedin, boolean isingame, int gamesplayed, int wins, int draws, int losses) {
        this.userid = userid;
        this.username = username;
        this.isloggedin = isloggedin;
        this.isingame = isingame;
        this.gamesplayed = gamesplayed;
        this.wins = wins;
        this.draws = draws;
        this.losses = losses;
        score = wins - losses;
    }

    public Player(ResultSet rs){
        try {

            userid =rs.getInt("userid");
            username = rs.getString("username");
            isloggedin = rs.getBoolean("isloggedin");
            isingame = rs.getBoolean("isingame");
            gamesplayed = rs.getInt("gamesplayed");
            wins= rs.getInt("wins");
            draws =rs.getInt("draws");
            losses=rs.getInt("losses");
            score=rs.getInt("score");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Player(String username, boolean isloggedin, boolean isingame, int gamesplayed, int wins, int draws,
            int losses, int score) {
        this.username = username;
        this.isloggedin = isloggedin;
        this.isingame = isingame;
        this.gamesplayed = gamesplayed;
        this.wins = wins;
        this.draws = draws;
        this.losses = losses;
        this.score = score;
    }

    public int getUserid() {
        return userid;
    }
    public void setUserid(int userid) {
        this.userid = userid;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public boolean isIsloggedin() {
        return isloggedin;
    }
    public void setIsloggedin(boolean isloggedin) {
        this.isloggedin = isloggedin;
    }
    public boolean isIsingame() {
        return isingame;
    }
    public void setIsingame(boolean isingame) {
        this.isingame = isingame;
    }
    public int getGamesplayed() {
        return gamesplayed;
    }
    public void setGamesplayed(int gamesplayed) {
        this.gamesplayed = gamesplayed;
    }
    public int getWins() {
        return wins;
    }
    public void setWins(int wins) {
        this.wins = wins;
    }
    public int getDraws() {
        return draws;
    }
    public void setDraws(int draws) {
        this.draws = draws;
    }
    public int getLosses() {
        return losses;
    }
    public void setLosses(int losses) {
        this.losses = losses;
    }
    public void setScore(){
        score = wins-losses;
    }
    public void addLose(){
        losses++;
        score--;
    }
    public int getScore(){
        return score;
    }
    public void addWin(){
        wins++;
        score++;
    }
    public void addDraw(){
        draws++;
    }
    @Override
    public String toString() {
        return "{\"userid\":" + userid + ", \"username\":\"" + username + "\", \"isloggedin\":" + isloggedin + ", \"isingame\":"
                + isingame + ", \"gamesplayed\":" + gamesplayed + ", \"wins\":" + wins + ", \"draws\":" + draws + ", \"losses\":"
                + losses + ", \"score\":" + score + "}";
    }


    public static Player fromJson(String json){
//        return gson.fromJson(json, Player.class);
        JSONObject object = new JSONObject(json);
        int userid =  object.getInt("userid");
        String username = object.getString("username");
        boolean isloggedin = object.getBoolean("isloggedin");
        boolean isingame= object.getBoolean("isingame");
        int gamesplayed= object.getInt("gamesplayed");
        int wins= object.getInt("wins");
        int draws= object.getInt("draws");
        int losses=object.getInt("losses");
        return new  Player(userid,username,isloggedin,isingame,gamesplayed,wins,draws,losses);
    }
    
}
