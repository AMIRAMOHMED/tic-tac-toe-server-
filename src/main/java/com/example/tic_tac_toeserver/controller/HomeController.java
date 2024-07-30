package com.example.tic_tac_toeserver.controller;

import com.example.tic_tac_toeserver.database.apiFunctions;
import com.example.tic_tac_toeserver.logic.Server;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HomeController {

    @FXML
    private Label welcomeText;

    @FXML
    private Button serverStatusButton;



    @FXML
    private BarChart<String, Number> userStatusChart;

    private boolean isServerOpen = true;
    private Server server;

    private int onlineUsers = 0;
    private int offlineUsers = 0;

    @FXML
    public void initialize() {
        server = new Server();
        server.startServer(); // Start the server by default
        fetchUserStatus();

        updateServerStatusButton();
        updateUserStatusChart();
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
                } else if (resultSet.getInt("isloggedin") == 0) {
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


    private void updateUserStatusChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Users");

        series.getData().add(new XYChart.Data<>("Online Users", onlineUsers));
        series.getData().add(new XYChart.Data<>("Offline Users", offlineUsers));
        series.getData().add(new XYChart.Data<>("Total Users", onlineUsers + offlineUsers));

        userStatusChart.getData().clear();
        userStatusChart.getData().add(series);
        NumberAxis yAxis = (NumberAxis) userStatusChart.getYAxis();
        int maxUsers = Math.max(onlineUsers + offlineUsers, 10); // Ensure the upper bound is at least 10
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound((maxUsers / 10 + 1) * 10); // Round up to the nearest 10
        yAxis.setTickUnit(5);

    }

}