package com.example.tic_tac_toeserver.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class DatabaseConnection {
    Connection con;
    public  DatabaseConnection(){

        try{

            String url = "jdbc:mysql://localhost:3306/tictactoe";
            String userName=    "root";
            String password = "123456789";
            con = DriverManager.getConnection(url,userName,password);
        }catch (SQLException e){
            e.getErrorCode();

        }
    }

    public  ResultSet getData(String query){
        try {
            Statement stm =con.createStatement();
            return  stm.executeQuery(query);
        } catch (SQLException e) {
           e.printStackTrace();
           return null;
        }


    }

    public  int insertData(String query){
        try {
            Statement stm=con.createStatement();
            return stm.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
          return  0;
        }

    }
public void closeConnection(){
    try {
        if(con != null&& !con.isClosed()){
            con.close();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}



}
