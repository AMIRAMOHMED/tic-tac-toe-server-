package com.example.tic_tac_toeserver.services;

import com.example.tic_tac_toeserver.database.DatabaseConnection;
import com.example.tic_tac_toeserver.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserServices {

    private DatabaseConnection db;

    public UserServices() {
        db = new DatabaseConnection();
    }

    public boolean addUser(User user) {
        String query = "INSERT INTO user (username, email, password) VALUES ('"
                + user.getUsername() + "', '"
                + user.getEmail() + "', '"
                + user.getPassword() + "')";
        return db.insertData(query) > 0;
    }

    public User getUserById(int userId) {
        String query = "SELECT * FROM user WHERE userid = " + userId;
        try (ResultSet rs = db.getData(query)) {
            if (rs.next()) {
                return new User(
                        rs.getInt("userid"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isUserById(int userId) {
        String query = "SELECT 1 FROM user WHERE userid = " + userId;
        try (ResultSet rs = db.getData(query)) {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}