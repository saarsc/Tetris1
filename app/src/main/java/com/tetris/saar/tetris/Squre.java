package com.tetris.saar.tetris;

//Class for the square
public class Squre extends Blocks{

    public Squre(int i, int j){
       super(false, true,true, false,true,false, false, false,1,i,j);
    }
    public Squre(Squre line , int i, int j){
        super(line,i,j);
    }

}
