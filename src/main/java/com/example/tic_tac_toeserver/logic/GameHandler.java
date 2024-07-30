package com.example.tic_tac_toeserver.logic;

import com.example.tic_tac_toeserver.models.GameMoves;
import com.example.tic_tac_toeserver.models.PlayBoard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.UUID;

public class GameHandler extends Thread{
    public String id = UUID.randomUUID().toString().split("-")[0];
    GameMoves gameMoves;
    ArrayList<Integer> moves;
    PlayBoard board;
    UserHandler user;
    UserHandler opponent;
    boolean flag;
    PrintWriter userOutput;
    PrintWriter opponentOutput;
    BufferedReader userInput;
    BufferedReader opponentInput;
    public GameHandler(UserHandler user, UserHandler opponent){
        this.user = user;
        this.opponent = opponent;
        try {
            userOutput = new PrintWriter(user.getUserSocket().getOutputStream());
            opponentOutput = new PrintWriter(opponent.getUserSocket().getOutputStream());
            userInput = new BufferedReader(new InputStreamReader(user.getUserSocket().getInputStream()));
            opponentInput = new BufferedReader(new InputStreamReader(opponent.getUserSocket().getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        gameMoves= new GameMoves();
        moves = new ArrayList<Integer>();
        board = new PlayBoard();
        flag = true;
        start();
    }

    @Override
    public void run() {
        int i = -1;
        while (i<0) {
            try {
                int index = Integer.parseInt(userInput.readLine());
                i = board.play(index, flag ? 'X' : 'O');
                opponentOutput.println("{\"RequestType\":\"InGame\", \"play\":"+index+"}");
                if (i== 0 || i == 1){
                    setWinner();
                }
                flag = !flag ;
            } catch (Exception e ){
                flag = !flag;
                setWinner();
                break;
            }
            try {
                int index = Integer.parseInt(opponentInput.readLine());
                i = board.play(index, flag ? 'X' : 'O');
                userOutput.println("{\"RequestType\":\"InGame\" , \"play\":"+index+"}");
                flag = !flag ;
            } catch (Exception e ){
                flag = !flag;
                setWinner();
                break;
            }
        }
        if ( i==1) setWinner();
        else if (i == 0) setDraw();
    }
    private void setWinner(){
        if (flag) {
            user.getPlayer().addWin();
            opponent.getPlayer().addLose();
            user.send("{\"RequestType\":\"EndGame\", \"Value\":"+1+"}");
            opponent.send("{\"RequestType\":\"EndGame\", \"Value\":"+-1+"}");
        }
        else {
            opponent.getPlayer().addWin();
            user.getPlayer().addLose();
            opponent.send("{\"RequestType\":\"EndGame\", \"Value\":"+1+"}");
            user.send("{\"RequestType\":\"EndGame\", \"Value\":"+-1+"}");
        }
    }
    private void setDraw(){
        user.getPlayer().addDraw();
        opponent.getPlayer().addDraw();
        user.send("{\"RequestType\":\"EndGame\", \"Value\":"+0+"}");
        opponent.send("{\"RequestType\":\"EndGame\", \"Value\":"+0+"}");
    }

}
