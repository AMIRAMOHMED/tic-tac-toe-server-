package com.example.tic_tac_toeserver.controller;

import com.example.tic_tac_toeserver.logic.Server;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class  HomeController {

    @FXML
    private Label welcomeText;

    @FXML
    private Button serverStatusButton;

    @FXML
    private ListView<User> userListView;

    private boolean isServerOpen = true;
    private ObservableList<User> userList;
    private Server server;

    @FXML
    public void initialize() {
        server = new Server();
        server.startServer(); // Start the server by default

        userList = FXCollections.observableArrayList(
                new User("Alice", true),
                new User("Bob", false),
                new User("Charlie", true),
                new User("Diana", false)
        );

        userListView.setItems(userList);
        userListView.setCellFactory(param -> new ListCell<User>() {
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                if (empty || user == null) {
                    setText(null);
                } else {
                    Text text = new Text(user.getName() + " - " + (user.isOnline() ? "Online" : "Offline"));
                    text.setFill(user.isOnline() ? Color.GREEN : Color.RED);
                    setGraphic(text);
                }
            }
        });

        // Set initial server status button text and color
        updateServerStatusButton();
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

    private void updateServerStatusButton() {
        if (isServerOpen) {
            serverStatusButton.setText("Server Status: Open");
            serverStatusButton.setStyle("-fx-background-color: green;");
        } else {
            serverStatusButton.setText("Server Status: Closed");
            serverStatusButton.setStyle("-fx-background-color: red;");
        }
    }

    // User class to represent the user data
    public static class User {
        private String name;
        private boolean isOnline;

        public User(String name, boolean isOnline) {
            this.name = name;
            this.isOnline = isOnline;
        }

        public String getName() {
            return name;
        }

        public boolean isOnline() {
            return isOnline;
        }
    }
}