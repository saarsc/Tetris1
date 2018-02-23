package com.tetris.saar.tetris;

/**
 * The type Squre.
 */
//Class for the square
public class Squre extends Blocks{

    /**
     * Instantiates a new Squre.
     *
     * @param i the
     * @param j the j
     */
    public Squre(int i, int j){
       super(false, true,true, false,true,false, false, false,1,i,j);
    }

    /**
     * Instantiates a new Squre.
     *
     * @param line the line
     * @param i    the
     * @param j    the j
     */
    public Squre(Squre line , int i, int j){
        super(line,i,j);
    }

}
