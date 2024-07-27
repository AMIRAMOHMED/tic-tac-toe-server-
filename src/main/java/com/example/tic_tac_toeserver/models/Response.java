package com.example.tic_tac_toeserver.models;

import com.example.tic_tac_toeserver.constants.RequestType;

public class Response {
    Response(){}
    public static void getResponse(RequestType type){
        switch (type) {
            case Register:
                
                break;
            case Login:
                
                break;
            case RequestGame:
                
                break;
            case PlayAgain:
                
                break;
            case Surrender:
                
                break;
            case PlayerList:
                
                break;
            case Scoreboard:
                
                break;
            case GameHistory:
                
                break;
            default:
                break;
        }
    }
}
