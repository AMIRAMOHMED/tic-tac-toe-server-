package com.example.tic_tac_toeserver.models;

import java.io.*;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private DatabaseHandler dbHandler;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.dbHandler = new DatabaseHandler();
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            dbHandler.connect();

            String request;
            while ((request = in.readLine()) != null) {
                String[] requestParts = request.split(" ");
                String command = requestParts[0];

                switch (command) {
                    case "LOGIN":
                        handleLogin(requestParts, out);
                        break;
                    case "REGISTER":
                        handleRegister(requestParts, out);
                        break;
                    default:
                        out.println("UNKNOWN_COMMAND");
                }
            }

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                dbHandler.disconnect();
                clientSocket.close();
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleLogin(String[] requestParts, PrintWriter out) throws SQLException {
        if (requestParts.length < 3) {
            out.println("ERROR INVALID_LOGIN_REQUEST");
            return;
        }

        String username = requestParts[1];
        String password = requestParts[2];

        User user = dbHandler.fetchUser(username);
        if (user != null && user.getPassword().equals(password)) {
            out.println("LOGIN_SUCCESS");
        } else {
            out.println("LOGIN_FAILED");
        }
    }

    private void handleRegister(String[] requestParts, PrintWriter out) throws SQLException {
        if (requestParts.length < 3) {
            out.println("ERROR INVALID_REGISTER_REQUEST");
            return;
        }

        String username = requestParts[1];
        String password = requestParts[2];

        User existingUser = dbHandler.fetchUser(username);
        if (existingUser == null) {
            User newUser = new User(username, password);
            String query = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement stmt = dbHandler.getConnection().prepareStatement(query);
            stmt.setString(1, newUser.getUsername());
            stmt.setString(2, newUser.getPassword());
            stmt.executeUpdate();
            out.println("REGISTER_SUCCESS");
        } else {
            out.println("REGISTER_FAILED_USER_EXISTS");
        }
    }
}
