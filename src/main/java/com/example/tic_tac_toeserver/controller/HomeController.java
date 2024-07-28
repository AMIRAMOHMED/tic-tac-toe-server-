package com.example.tic_tac_toeserver.controller;

import com.example.tic_tac_toeserver.database.apiFunctions;
import com.example.tic_tac_toeserver.logic.Server;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HomeController {

    @FXML
    private Label welcomeText;

    @FXML
    private Button serverStatusButton;

    @FXML
    private Label onlineUsersLabel;

    @FXML
    private Label offlineUsersLabel;

    private boolean isServerOpen = true;
    private Server server;

    private int onlineUsers = 0;
    private int offlineUsers = 0;

    @FXML
    public void initialize() {
        server = new Server();
        server.startServer(); // Start the server by default
        fetchUserStatus();

        // Set initial server status button text and color
        updateServerStatusButton();
        updateUsersStatus();
    }

    @FXML
    protected void onServerStatusButtonClick() {
        if (isServerOpen) {
            server.stopServer();
        } else {
            server = new Server();
            server.startServer();
        }
        isServerOpen = !isServerOpen;
        updateServerStatusButton();
    }

    private void fetchUserStatus() {
        apiFunctions api = new apiFunctions();
        ResultSet resultSet = api.read(
                "SELECT isloggedin, COUNT(*) as count FROM player GROUP BY isloggedin");

        try {
            while (resultSet.next()) {
                if (resultSet.getInt("isloggedin") == 1) {
                    onlineUsers = resultSet.getInt("count");
                } else {
                    offlineUsers = resultSet.getInt("count");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateServerStatusButton() {
        if (isServerOpen) {
            serverStatusButton.setText("Server Status: Open");
            serverStatusButton.setStyle("-fx-background-color: green;");
        } else {
            serverStatusButton.setText("Server Status: Closed");
            serverStatusButton.setStyle("-fx-background-color: red;");
        }
    }

    private void updateUsersStatus() {
        onlineUsersLabel.setText("Online Users: " + onlineUsers);
        offlineUsersLabel.setText("Offline Users: " + offlineUsers);
    }
}