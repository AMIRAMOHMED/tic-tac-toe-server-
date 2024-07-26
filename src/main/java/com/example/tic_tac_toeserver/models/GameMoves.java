package com.example.tic_tac_toeserver.models;import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class GameMoves {
    private String player1;
    private String player2;
    private ArrayList<Integer> moves;
    public GameMoves() {
        moves = new ArrayList<Integer>();
    }
    public GameMoves(String player1, String player2, ArrayList<Integer> moves) {
        this.player1 = player1;
        this.player2 = player2;
        this.moves = moves;
    }
    public String getPlayer1() {
        return player1;
    }
    public void setPlayer1(String player1) {
        this.player1 = player1;
    }
    public String getPlayer2() {
        return player2;
    }
    public void setPlayer2(String player2) {
        this.player2 = player2;
    }
    public ArrayList<Integer> getMoves() {
        return moves;
    }
    public void setMoves(ArrayList<Integer> moves) {
        this.moves = moves;
    }
    

    @Override
    public String toString() {
        return "{\"player1\":\"" + player1 + "\", \"player2\":\"" + player2 + "\", \"moves\":[" + moves + "]}";
    }
    public void toGameMoves(String query){
        JSONObject object = new JSONObject(query);
        player1 = object.getString("player1");
        player2 = object.getString("player2");
        JSONArray movesarr = object.getJSONArray("moves");
        if (movesarr != null){
            for (int i =0 ; i<movesarr.length();i++){
                moves.add(movesarr.optInt(i));
            }
        }

    }
}
