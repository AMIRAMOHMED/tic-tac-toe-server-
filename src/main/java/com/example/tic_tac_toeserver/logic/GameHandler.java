package com.example.tic_tac_toeserver.logic;

import com.example.tic_tac_toeserver.models.GameMoves;
import com.example.tic_tac_toeserver.models.PlayBoard;

import java.util.ArrayList;
import java.util.UUID;

public class GameHandler extends Thread{
    public String id = UUID.randomUUID().toString().split("-")[0];
    GameMoves gameMoves;
    ArrayList<Integer> moves;
    PlayBoard board;
    PlayerHandler user;
    PlayerHandler opponent;
    GameHandler(PlayerHandler user , PlayerHandler opponent){
        this.user = user;
        this.opponent = opponent;
        gameMoves= new GameMoves();
        moves = new ArrayList<Integer>();
        board = new PlayBoard();
        this.start();
    }
    @Override
    public void run() {
        System.out.println("Entered Game Hanndler");
        int i = 0;
        while (true) {
                endGame(user,opponent,board.play(i,'X'));
                endGame(opponent,user,board.play(i,'O'));
        }

    }

    public void endGame(PlayerHandler winner, PlayerHandler loser, int flag){
        switch (flag){
            case 0:
                interrupt();
                winner.getPlayer().addDraw();
                loser.getPlayer().addDraw();
                break;
            case 1:
                interrupt();
                winner.getPlayer().addWin();
                loser.getPlayer().addLose();
                break;
            default:
                break;
        }
    }
}
