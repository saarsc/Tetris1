package com.tetris.saar.tetris;

/**
 * Created by user on 09/08/2017.
 */

public class GameManger {
    int[][] board;
    public GameManger(){
        this.board = new int [10][24];
        init();
    }
    public void init(){
        for(int i=0; i< this.board.length; i++){
            for (int j=0; j< this.board[i].length; j++){
                this.board[i][j] = 0;
            }
        }
    }
    public int[][] getDisplay(){
        return this.board;
    }
}
