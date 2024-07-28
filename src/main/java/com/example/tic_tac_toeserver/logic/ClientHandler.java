package com.example.tic_tac_toeserver.logic;
import com.example.tic_tac_toeserver.models.Player;
import com.example.tic_tac_toeserver.models.Response;
import java.io.*;
import java.net.Socket;
import java.util.Vector;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    Player player;
    public static Vector<ClientHandler> clients;
    static {
        clients = new Vector<ClientHandler>();
    }
    public ClientHandler(Socket clientSocket, Player player) {
        this.clientSocket = clientSocket;
        this.player = player;
        clients.add(this);
        start();
    }
    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            String request;
            while ((request = in.readLine()) != null) {
                Response.getResponse(request);
            }
        } catch (IOException e) {
            try {
                this.interrupt();
                this.clientSocket.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            clients.remove(this);
            e.printStackTrace();
        }
    }

    public Socket getClientSocket() {
        return clientSocket;
    }
}
