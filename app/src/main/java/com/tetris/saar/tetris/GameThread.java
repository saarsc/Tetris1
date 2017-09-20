package com.tetris.saar.tetris;

import android.os.Handler;

/**
 * Created by user on 27/08/2017.
 */

public class GameThread extends Thread {
    GameManger gm;
    Handler uiHandle;
  static   boolean change =false;
    static boolean right = false;
    static  boolean left =false;

    public GameThread(GameManger gm){
        this.gm = gm;
        uiHandle = new Handler();
    }

    @Override
    public void run(){
        int i=0;
        while(true) {
            Blocks currentBlock = gm.pickBlock();
            gm.insertBlock(currentBlock);
            while (currentBlock.isMoving()) {
                //Handle the UI update(can not be done in different Threads)
                update();
                try {
                    Thread.sleep(gm.getDropSpeed());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (gm.isEmptyDown(currentBlock)) {
                    gm.moveDown(currentBlock);
                }
                if(change){
                    gm.change(currentBlock);
                    update();;
                    this.change = false;
                }
                if(right){
                    gm.moveRight(currentBlock);
                    update();
                    right = false;
                }
                if(left){
                    gm.moveLeft(currentBlock);
                    update();
                    left= false;
                }
            }
            gm.bugFixEmptyRow(currentBlock);
            gm.checkBoard();
            i++;
        }

    }
    public void update(){
        uiHandle.post(new Runnable() {
            @Override
            public void run() {
                gm.gameActivity.toDisplay();
            }
        });
    }
    public void needToChange(){
        this.change = true;
    }
    public void moveRight(){
        right = true;
    }
    public void moveLeft(){
        left=true;
    }
    public void changeText(){

    }

}
