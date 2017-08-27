package com.tetris.saar.tetris;

import android.os.Handler;

/**
 * Created by user on 27/08/2017.
 */

public class GameThread extends Thread {
    GameManger gm;
    Handler uiHandle;
    public GameThread(GameManger gm){
        this.gm = gm;
        uiHandle = new Handler();
    }
    @Override
    public void run(){
        int i=0;
        while(i< 2) {
            Blocks currentBlock = gm.pickBlock();
            gm.insertBlock(currentBlock);

            while (currentBlock.isMoving()) {
                //Handle the UI update(can not be done in different Threads)
                uiHandle.post(new Runnable() {
                    @Override
                    public void run() {
                        gm.gameActivity.toDisplay();
                    }
                });

                try {
                    Thread.sleep(gm.getDropSpeed());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (gm.isEmptyDown(currentBlock)) {
                    gm.moveDown(currentBlock);
                }

            }
            i++;
        }
    }



}
