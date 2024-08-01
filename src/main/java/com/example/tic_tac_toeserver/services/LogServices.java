package com.example.tic_tac_toeserver.services;

import com.example.tic_tac_toeserver.database.DatabaseConnection;
import com.example.tic_tac_toeserver.models.GameMoves;
import com.example.tic_tac_toeserver.models.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class LogServices {

    private DatabaseConnection db;

    public LogServices() {
        db = new DatabaseConnection();
    }

    public Log getLogByGameId(int gameId) {
        String query = "SELECT * FROM game_log WHERE gameid = " + gameId;
        try (ResultSet rs = db.getData(query)) {
            if (rs.next()) {
                int userid = rs.getInt("userid");
                int opponentid = rs.getInt("opponentid");
                Date gameDate = rs.getTimestamp("gameDate");
                GameMoves gameMoves = GameMoves.toGameMoves(rs.getString("gameMoves")); // Assuming GameMoves has a fromString method

                return new Log(gameId, userid, opponentid, gameDate, gameMoves);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Log getLogByUserId(int userId) {
        String query = "SELECT * FROM game_log WHERE userid = " + userId;
        try (ResultSet rs = db.getData(query)) {
            if (rs.next()) {
                int gameid = rs.getInt("gameid");
                int opponentid = rs.getInt("opponentid");
                Date gameDate = rs.getTimestamp("gameDate");
                GameMoves gameMoves = GameMoves.toGameMoves(rs.getString("gameMoves"));

                return new Log(gameid, userId, opponentid, gameDate, gameMoves);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Log getLogByOpponentId(int opponentId) {
        String query = "SELECT * FROM game_log WHERE opponentid = " + opponentId;
        try (ResultSet rs = db.getData(query)) {
            if (rs.next()) {
                int gameid = rs.getInt("gameid");
                int userid = rs.getInt("userid");
                Date gameDate = rs.getTimestamp("gameDate");
                GameMoves gameMoves = GameMoves.toGameMoves(rs.getString("gameMoves"));

                return new Log(gameid, userid, opponentId, gameDate, gameMoves);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean setLog(Log log) {
        String query = "INSERT INTO game_log (gameid, userid, opponentid, gameDate, gameMoves) VALUES ("
                + log.getGameid() + ", "
                + log.getUserid() + ", "
                + log.getOpponentid() + ", '"
                + new Timestamp(log.getGameDate().getTime()) + "', '"
                + log.getGameMoves().toString() + "')";

        return db.insertData(query) > 0;
    }
}