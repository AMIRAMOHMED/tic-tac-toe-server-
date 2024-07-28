package com.example.tic_tac_toeserver.logic;//


import com.example.tic_tac_toeserver.models.GameMoves;
import com.example.tic_tac_toeserver.models.PlayBoard;
import com.example.tic_tac_toeserver.models.Player;
import com.example.tic_tac_toeserver.models.Response;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    ClientHandler user;
    ClientHandler opponent;
    DataInputStream userinput;
    DataOutput useroutput;
    DataInputStream opponentinput;
    DataOutput opponentoutput;
    GameMoves gameMoves;
    ArrayList<Integer> moves;
    PlayBoard board;

    public Server() {
        try {
            serverSocket = new ServerSocket(5005, 0, InetAddress.getByName("192.168.1.3"));
            gameMoves= new GameMoves();
            moves = new ArrayList<Integer>();
            board = new PlayBoard();
            System.out.println("Server started on port 5005");
            while(true) {
                clientSocket = serverSocket.accept();
                user = new ClientHandler(clientSocket,new Player("filo",true,true,0,0,0,0,0));
            System.out.println("user connected!!");
                clientSocket = serverSocket.accept();
                opponent = new ClientHandler(clientSocket,new Player("amira",true,true,0,0,0,0,0));
            System.out.println("opponent connected!!");
                userinput = new DataInputStream(user.getClientSocket().getInputStream());
                opponentinput = new DataInputStream(opponent.getClientSocket().getInputStream());
                useroutput= new DataOutputStream(user.getClientSocket().getOutputStream());
                opponentoutput = new DataOutputStream(opponent.getClientSocket().getOutputStream());
                System.out.println("Entered Game Hanndler");
                int i = 0;
                try {
                    while (true) {
                        System.out.println("Listening");
                        String s = userinput.readUTF();
                        System.out.println(s);
                        i = Integer.getInteger(s);
                        System.out.println(i);
                        moves.add(i);
                        opponentoutput.writeUTF(""+i);
                        endGame(user,opponent,board.play(i,'X'));
                        s = opponentinput.readUTF();
                        System.out.println(s);
                        i= Integer.getInteger(s);
                        moves.add(i);
                        useroutput.writeUTF(""+i);
                        endGame(opponent,user,board.play(i,'O'));
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
//                input = new DataInputStream(clientSocket.getInputStream());
//                output = new DataOutputStream(clientSocket.getOutputStream());
//                String s = input.readUTF();
//                System.out.println(s);
//                String response = Response.getResponse(s);
//                output.writeUTF(response);
            }
        } catch (IOException var2) {
            IOException e = var2;
            e.printStackTrace();
        }
    }
    public void endGame(ClientHandler winner, ClientHandler loser,int flag){
        switch (flag){
            case 0:
                winner.player.addDraw();
                loser.player.addDraw();
                break;
            case 1:
                winner.player.addWin();
                loser.player.addLose();
                break;
            default:
                break;
        }
    }
    public static void main(String[] args) {
        new Server();
    }
}
