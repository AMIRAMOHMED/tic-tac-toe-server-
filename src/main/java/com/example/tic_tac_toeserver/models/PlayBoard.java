package com.example.tic_tac_toeserver.models;

public class PlayBoard {

    private final char[][] board;
    private int counter;
    private int[][] winningTiles;

    public PlayBoard() {
        counter = 0;
        winningTiles = new int[3][2];
        board = new char[][]{{'_', '_', '_'}, {'_', '_', '_'}, {'_', '_', '_'}};
    }

    public char[][] getBoard() {
        return board;
    }

        /**
     * @param x row Position
     * @param y Column Position
     * @param symbol Player Symbol
     * @return -1 if none win, 0 if it was a draw, 1 if the last player win
     * @since Add the Symbol to a position then checks if that symbol is in win
     * condition
     */
    public int play(int x, int y, char symbol) {
        int col = 0, row = 0, diag = 0, rdiag = 0;
        board[x][y] = symbol;
        counter++;
        int win = -1;

        if (counter > 4) {
            for (int i = 0; i < 3; i++) {
                if (board[x][i] == symbol) row++;
                if (board[i][y] == symbol) col++;
                if (board[i][i] == symbol) diag++;
                if (board[i][2 - i] == symbol) rdiag++;

            }
            if (row == 3) {
                win = 1; // Win by row
                for (int i = 0; i < 3; i++) winningTiles[i] = new int[]{x, i};
            } else if (col == 3) {
                win = 1; // Win by column
                for (int i = 0; i < 3; i++) winningTiles[i] = new int[]{i, y};
            } else if (diag == 3) {
                win = 1; // Win by main diagonal
                for (int i = 0; i < 3; i++) winningTiles[i] = new int[]{i, i};
            } else if (rdiag == 3) {
                win = 1; // Win by reverse diagonal
                for (int i = 0; i < 3; i++) winningTiles[i] = new int[]{i, 2 - i};
            }
           
        }
        if (win == -1 && counter == 9) {
            win = 0; // Draw
        }

        return win;
    }

    public  int play(int i,char symbol){
        int x=0,y=0;
        for(int j =0 ; j<i;j++){
            if(i==3) x++;
            y=i%3;
        }
        int col = 0, row = 0, diag = 0, rdiag = 0;
        board[x][y] = symbol;
        counter++;
        int win = -1;

        if (counter > 4) {
            for (int j = 0; j < 3; j++) {
                if (board[x][j] == symbol) row++;
                if (board[j][y] == symbol) col++;
                if (board[j][j] == symbol) diag++;
                if (board[j][2 - j] == symbol) rdiag++;

            }
            if (row == 3) {
                win = 1; // Win by row
                for (int j = 0; j < 3; j++) winningTiles[j] = new int[]{x, j};
            } else if (col == 3) {
                win = 1; // Win by column
                for (int j = 0; j < 3; j++) winningTiles[j] = new int[]{j, y};
            } else if (diag == 3) {
                win = 1; // Win by main diagonal
                for (int j = 0; j < 3; j++) winningTiles[j] = new int[]{j, j};
            } else if (rdiag == 3) {
                win = 1; // Win by reverse diagonal
                for (int j = 0; j < 3; j++) winningTiles[j] = new int[]{j, 2 - j};
            }

        }
        if (win == -1 && counter == 9) {
            win = 0; // Draw
        }

        return win;
    }


    public int[][] getWinningTiles() {
        return winningTiles;
    }
}