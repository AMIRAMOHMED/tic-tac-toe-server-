package com.example.tic_tac_toeserver.database;

import java.sql.ResultSet;

public class apiFunctions {


    private  DatabaseConnection db;

    public  apiFunctions(){

    }

    public  int create(String query) {
        db = new DatabaseConnection();
        return db.insertData(query);
    }

    public ResultSet read(String query) {
        db = new DatabaseConnection();
        return db.getData(query);
    }

    public int update(String query) {
        db = new DatabaseConnection();
        return db.insertData(query);
    }

    public int delete(String query) {
        db = new DatabaseConnection();
        return db.insertData(query);
    }
}
