package com.example.tic_tac_toeserver.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class apiFunctions {


    private  DatabaseConnection db;

    public  apiFunctions(){
        db = new DatabaseConnection();
    }

    public  int create(String query) {
        return db.insertData(query);
    }

    public ResultSet read(String query) {
        return db.getData(query);
    }

    public int update(String query) {
        return db.insertData(query);
    }

    public int delete(String query) {
        return db.insertData(query);
    }

    public String getUsernameById(int playerId) {
        String username = null;
        String query = "SELECT username FROM player WHERE id = " + playerId;
        try (ResultSet rs = read(query)) {
            if (rs.next()) {
                username = rs.getString("username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return username;
    }
}
