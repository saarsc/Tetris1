package com.tetris.saar.tetris;

/**
 * Created by user on 08/10/2017.
 */

public class LandBLockThread extends Thread {
    Blocks block;
    GameManger gm;
    public LandBLockThread(Blocks landBlock,GameManger gm){
        this.block = landBlock;
        this.gm = gm;
    }
    public void run(){
        while(gm.isEmptyDown(block)){
            gm.moveDown(block);
        }
    }
}
