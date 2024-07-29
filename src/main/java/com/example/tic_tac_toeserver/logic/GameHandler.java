package com.example.tic_tac_toeserver.logic;

import com.example.tic_tac_toeserver.models.GameMoves;
import com.example.tic_tac_toeserver.models.PlayBoard;
import com.example.tic_tac_toeserver.models.User;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.UUID;

public class GameHandler extends Thread{
    public String id = UUID.randomUUID().toString().split("-")[0];
    GameMoves gameMoves;
    ArrayList<Integer> moves;
    PlayBoard board;
    UserHandler user;
    UserHandler opponent;
    String response;
    JSONObject obj;
    public GameHandler(UserHandler user, UserHandler opponent){
        this.user = user;
        this.opponent = opponent;
        gameMoves= new GameMoves();
        moves = new ArrayList<Integer>();
        board = new PlayBoard();
        this.start();
    }
    @Override
    public void run() {
        opponent.send("{\"RequestType\":\"RequestGame\",\"Player\":"+user.getPlayer().toString()+"}");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(opponent.getUserSocket().getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                obj = new JSONObject(reader.readLine());
                response = obj.toString();
                user.send(response);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Entered Game Hanndler");
        if (obj.getBoolean("accepted")) {
            handleGame();
        }
        else this.interrupt();

    }
    public void handleGame(){
        int i = 0;
        while (true) {
            endGame(user, opponent, board.play(i, 'X'));
            endGame(opponent, user, board.play(i, 'O'));
        }
    }

    public void endGame(UserHandler winner, UserHandler loser, int flag){
        switch (flag){
            case 0:
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
