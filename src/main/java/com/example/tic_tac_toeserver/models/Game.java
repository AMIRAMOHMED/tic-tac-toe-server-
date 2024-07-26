package com.example.tic_tac_toeserver.models;

import java.util.Date;

public class Game {
    private User player1;
    private User player2;
    private char[][] board;
    private boolean isGameActive;
    private boolean isPlayer1Turn;
    private Date startTime;
    private Date endTime;

    public Game(User player1, User player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.board = new char[3][3]; // Assuming 3x3 board for Tic-Tac-Toe
        this.isGameActive = true;
        this.isPlayer1Turn = true;
    }

    public User getPlayer1() {
        return player1;
    }

    public void setPlayer1(User player1) {
        this.player1 = player1;
    }

    public User getPlayer2() {
        return player2;
    }

    public void setPlayer2(User player2) {
        this.player2 = player2;
    }

    public char[][] getBoard() {
        return board;
    }

    public void setBoard(char[][] board) {
        this.board = board;
    }

    public boolean isGameActive() {
        return isGameActive;
    }

    public void setGameActive(boolean isGameActive) {
        this.isGameActive = isGameActive;
    }

    public boolean isPlayer1Turn() {
        return isPlayer1Turn;
    }

    public void setPlayer1Turn(boolean isPlayer1Turn) {
        this.isPlayer1Turn = isPlayer1Turn;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public boolean makeMove(int row, int col, char symbol) {
        if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == '\0' && isGameActive) {
            board[row][col] = symbol;
            isPlayer1Turn = !isPlayer1Turn;
            return true;
        }
        return false;
    }

    public char checkWinner() {
        // Check rows, columns, and diagonals for a winner
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != '\0' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) return board[i][0];
            if (board[0][i] != '\0' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) return board[0][i];
        }
        if (board[0][0] != '\0' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) return board[0][0];
        if (board[0][2] != '\0' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) return board[0][2];
        return '\0';
    }

    public void endGame() {
        isGameActive = false;
    }
}

