package com.example.tic_tac_toeserver.services;


import com.example.tic_tac_toeserver.database.DatabaseConnection;
import com.example.tic_tac_toeserver.models.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerServices {

    private final DatabaseConnection db;

    public PlayerServices() {
        db = new DatabaseConnection();
    }

    public Player getPlayerById(int userId) {
        String query = "SELECT * FROM player WHERE userid = " + userId;
        try (ResultSet rs = db.getData(query)) {
            if (rs.next()) {
                return new Player(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setLogging(int userId, boolean isLoggedIn) {
        String query = "UPDATE player SET isloggedin = " + isLoggedIn + " WHERE userid = " + userId;
        db.insertData(query);
    }

    public void setInGame(int userId, boolean isInGame) {
        String query = "UPDATE player SET isingame = " + isInGame + " WHERE userid = " + userId;
        db.insertData(query);
    }

    public void addWin(int userId) {
        Player player = getPlayerById(userId);
        if (player != null) {
            player.addWin();
            player.setGamesplayed(player.getGamesplayed() + 1);
            player.setScore();

            String query = "UPDATE player SET wins = " + player.getWins()
                    + ", gamesplayed = " + player.getGamesplayed() +
                    ", score = " + player.getScore()
                    + " WHERE userid = " + userId;
            db.insertData(query);
        }
    }

    public void addLose(int userId) {
        Player player = getPlayerById(userId);
        if (player != null) {
            player.addLose();
            player.setGamesplayed(player.getGamesplayed() + 1);
            player.setScore();

            String query = "UPDATE player SET losses = "
                    + player.getLosses() + ", gamesplayed = "
                    + player.getGamesplayed()
                    + ", score = " + player.getScore() + " WHERE userid = " + userId;
            db.insertData(query);
        }
    }

    public void addDraw(int userId) {
        Player player = getPlayerById(userId);
        if (player != null) {
            player.addDraw();
            player.setGamesplayed(player.getGamesplayed() + 1);

            String query = "UPDATE player SET draws = "
                    + player.getDraws() +
                    ", gamesplayed = " +
                    player.getGamesplayed() +
                    " WHERE userid = " + userId;
            db.insertData(query);
        }
    }
}