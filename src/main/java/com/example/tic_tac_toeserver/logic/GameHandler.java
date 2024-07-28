package com.example.tic_tac_toeserver.logic;

import com.example.tic_tac_toeserver.models.GameMoves;
import com.example.tic_tac_toeserver.models.PlayBoard;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class GameHandler extends Thread{
    DataInputStream userinput;
    DataOutputStream useroutput;
    DataOutputStream opponentoutput;
    DataInputStream opponentinput;
    GameMoves gameMoves;
    ArrayList<Integer> moves;
    PlayBoard board;
    ClientHandler user;
    ClientHandler opponent;
    GameHandler(ClientHandler user , ClientHandler opponent){
        this.user = user;
        this.opponent = opponent;
        try {
            userinput = new DataInputStream(user.getClientSocket().getInputStream());
            opponentinput = new DataInputStream(opponent.getClientSocket().getInputStream());
            useroutput= new DataOutputStream(user.getClientSocket().getOutputStream());
            opponentoutput = new DataOutputStream(opponent.getClientSocket().getOutputStream());;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        gameMoves= new GameMoves();
        moves = new ArrayList<Integer>();
        board = new PlayBoard();
        this.start();
    }
    @Override
    public void run() {
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
    }

    public void endGame(ClientHandler winner, ClientHandler loser,int flag){
        switch (flag){
            case 0:
                interrupt();
                winner.player.addDraw();
                loser.player.addDraw();
                break;
            case 1:
                interrupt();
                winner.player.addWin();
                loser.player.addLose();
                break;
            default:
                break;
        }
    }
}
