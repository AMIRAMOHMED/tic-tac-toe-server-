package com.example.tic_tac_toeserver.models;import java.util.Date;

import org.json.JSONObject;
public class Log {
    private int gameid;
    private int userid;
    private int opponentid;
    private Date gameDate;
    private GameMoves gameMoves;
    public Log() {
    }
    public Log(int gameid, int userid, int opponentid, Date gameDate, GameMoves gameMoves) {
        this.gameid = gameid;
        this.userid = userid;
        this.opponentid = opponentid;
        this.gameDate = gameDate;
        this.gameMoves = gameMoves;
    }
    public int getGameid() {
        return gameid;
    }
    public void setGameid(int gameid) {
        this.gameid = gameid;
    }
    public int getUserid() {
        return userid;
    }
    public void setUserid(int userid) {
        this.userid = userid;
    }
    public int getOpponentid() {
        return opponentid;
    }
    public void setOpponentid(int opponentid) {
        this.opponentid = opponentid;
    }
    public Date getGameDate() {
        return gameDate;
    }
    public void setGameDate(Date gameDate) {
        this.gameDate = gameDate;
    }
    public GameMoves getGameMoves() {
        return gameMoves;
    }
    public void setGameMoves(GameMoves gameMoves) {
        this.gameMoves = gameMoves;
    }

    @Override
    public String toString() {
        return "{\"gameid\":" + gameid + ", \"userid\":" + userid + ", \"opponentid\":" + opponentid + ", \"gameDate\":" + gameDate
                + ", \"gameMoves\":[" + gameMoves.toString() + "]}";
    }
    public static Log toLogs(String query){
        JSONObject object = new JSONObject(query);
        int gameid = object.getInt("gameid");
        int userid = object.getInt("userid");
        int opponentid = object.getInt("opponentid");
        Date gameDate = (Date) object.get("gameDate");
        GameMoves move = GameMoves.toGameMoves(object.getString("gameMoves"));
        return new Log(gameid,userid,opponentid,gameDate,move);
    }
}
