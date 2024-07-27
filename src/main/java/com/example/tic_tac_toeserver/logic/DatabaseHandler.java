package com.example.tic_tac_toeserver.logic;


import com.example.tic_tac_toeserver.models.User;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class DatabaseHandler {
    private Connection connection;

    public DatabaseHandler() {
        // Initialize connection here
    }

    public void connect() throws SQLException {
        // Establish a database connection
        String url = "jdbc:mysql://localhost:3306/tic_tac_toe";
        String user = "root";
        String password = "password";
        connection = DriverManager.getConnection(url, user, password);
    }

    public void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public ResultSet executeQuery(String query) throws SQLException {
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }

    public int executeUpdate(String query) throws SQLException {
        Statement stmt = connection.createStatement();
        return stmt.executeUpdate(query);
    }

    public User fetchUser(String username) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String password = rs.getString("password");
            return new User(username, password);
        }
        return null;
    }

    public List<User> fetchAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        ResultSet rs = executeQuery(query);
        while (rs.next()) {
            String username = rs.getString("username");
            String password = rs.getString("password");
            users.add(new User(username, password));
        }
        return users;
    }


    public void updateUser(User user) throws SQLException {
        String query = "UPDATE users SET password = ? WHERE username = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, user.getPassword());
        stmt.setString(2, user.getUsername());
        stmt.executeUpdate();
    }
    public Connection getConnection() {
        return this.connection;
    }

}
