package com.example.tic_tac_toeserver.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class apiFunctions {


    private  DatabaseConnection db;

    public  apiFunctions(){
        db = new DatabaseConnection();
    }

    public  void create() {
        String query = "INSERT INTO yourtable (column1, column2) VALUES (value1, value2)";
        int result = db.insertData(query);
        if (result > 0) {
            System.out.println("Record created successfully.");
        } else {
            System.out.println("Failed to create record.");
        }
    }

    public void read() {
        String query = "SELECT * FROM yourtable";
        ResultSet rs = db.getData(query);
        try {
            while (rs.next()) {
                // Replace with your table's column names and types
                System.out.println("Column1: " + rs.getString("column1"));
                System.out.println("Column2: " + rs.getString("column2"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }
    public void update() {
        String query = "UPDATE yourtable SET column1 = value1 WHERE condition";
        int result = db.insertData(query);
        if (result > 0) {
            System.out.println("Record updated successfully.");
        } else {
            System.out.println("Failed to update record.");
        }
    }
    public void delete() {
        String query = "DELETE FROM yourtable WHERE condition";
        int result = db.insertData(query);
        if (result > 0) {
            System.out.println("Record deleted successfully.");
        } else {
            System.out.println("Failed to delete record.");
        }
    }
}
