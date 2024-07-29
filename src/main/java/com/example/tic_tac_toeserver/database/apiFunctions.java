package com.example.tic_tac_toeserver.database;

import java.sql.ResultSet;

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
}
