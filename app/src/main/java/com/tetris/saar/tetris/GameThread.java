package com.tetris.saar.tetris;

import android.os.Handler;

/**
 * Created by user on 27/08/2017.
 */

public class GameThread extends Thread {
    GameManger gm;
    Handler uiHandle;
  static   boolean change =false; //For changing the rotation
    static boolean right = false; //For moving right
    static  boolean left =false; //For moving left

    public GameThread(GameManger gm){
        this.gm = gm;
        uiHandle = new Handler(); //For displaying on screen while being on a different thread
    }
    //The main game
    @Override
    public void run(){

        //While the game is not over
        do {
           // Blocks nextBlock= gm.pickBlock();
            Blocks currentBlock =gm.pickBlock(); //Creating a new block
           // nextBlock = gm.pickBlock();
            gm.insertBlock(currentBlock); //Inserting the new block
            while (currentBlock.isMoving()) {
                //Handle the UI update(can not be done in different Threads)
                update();
                //Game ticks
                try {
                    Thread.sleep(gm.getDropSpeed());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //Moving down
                if (gm.isEmptyDown(currentBlock)) {
                    gm.moveDown(currentBlock);
                }
                //Changing the rotations
                if(change){
                    gm.change(currentBlock);
                    //update();;
                    this.change = false;
                }
                //Moving right
                if(right){
                    gm.moveRight(currentBlock);
                    //update();
                    right = false;
                }
                //Moving left
                if(left){
                    gm.moveLeft(currentBlock);
                    //update();
                    left= false;
                }
            }
            gm.setDropSpeed(250);
            gm.addPoistion(currentBlock.getPlace()); //Adding the end position of the block to the list
            gm.addBlock(currentBlock); //Adding the block to the list
            gm.bugFixEmptyRow(currentBlock); //Checking if the empty row bug happened
            gm.checkBoard(); //Checking for full rows
        }while((!gm.endOfGame()));

    }
    //Updating the screen
    public void update(){
        uiHandle.post(new Runnable() {
            @Override
            public void run() {
                gm.gameActivity.toDisplay();
            }
        });
    }
    //Change Block drop speed
    public void changeSpeed(){
        gm.setDropSpeed(10);
    }
    //Rotation button was pressed
    public void needToChange(){
        this.change = true;
    }
    //Move right was pressed
    public void moveRight(){
        right = true;
    }
    //Moving left was pressed
    public void moveLeft(){
        left=true;
    }

    public void changeText(){

    }

}
