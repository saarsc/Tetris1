package com.tetris.saar.tetris;

import android.os.Handler;

import java.util.ArrayList;

/**
 * Created by user on 27/08/2017.
 */

public class GameThread extends Thread {
    GameManger gm;
    Handler uiHandle;
  static   boolean change =false; //For changing the rotation
    static boolean right = false; //For moving right
    static  boolean left =false; //For moving left
    static boolean gameOver = false;
    int newNextBlock = 0;

    ArrayList<Blocks> blockList = new ArrayList<>();
    public GameThread(GameManger gm){
        this.gm = gm;
        uiHandle = new Handler(); //For displaying on screen while being on a different thread
    }

    //The main game
    @Override
    public void run() {
        init();
        Blocks currentBlock = blockList.get(0);
        //currentBlock.setNextBlock(gm.pickBlock());
        //gm.moveDown(currentBlock);
        //While the game is not over
        do {

            currentBlock = blockList.get(0);
            blockList.remove(0);
            //Blocks nextBlock= gm.pickBlock();
            //Creating a new block
            // nextBlock = gm.pickBlock();
            if(blockList.isEmpty()){
                init();
            }
            final Blocks finalNextBlock = blockList.get(0);
            uiHandle.post(new Runnable() {
                @Override
                public void run() {
                    gm.gameActivity.displayNextBlock(finalNextBlock);
                }
            });
            gm.insertBlock(currentBlock); //Inserting the new block
            gm.setEmptySpaceBlockPos(currentBlock.getNextBlock());
            gm.insertBlock(currentBlock.getNextBlock());
            gm.moveDown(currentBlock);
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
                if (change) {
                    gm.change(currentBlock);
                    //update();;
                    this.change = false;
                }
                //Moving right
                if (right) {
                    gm.moveRight(currentBlock);
                    //update();
                    right = false;
                }
                //Moving left
                if (left) {
                    gm.moveLeft(currentBlock);
                    //update();
                    left = false;
                }
            }
            gm.setDropSpeed(250);
            gm.addPoistion(currentBlock.getPlace()); //Adding the end position of the block to the list
            gm.addBlock(currentBlock); //Adding the block to the list
            gm.bugFixEmptyRow(currentBlock); //Checking if the empty row bug happened
            gm.checkBoard(); //Checking for full rows

            //currentBlock.setNextBlock(gm.pickBlock());
      /*      try {
                Thread.sleep(5);
            }catch (InterruptedException e){

            }*/

        } while ((!gm.endOfGame(currentBlock)));
    }
    //Creating a list of blocks to use
    public void init(){
        for(int i=0; i< 50; i++){
            Blocks temp = gm.pickBlock();
            temp.setNextBlock(new Blocks(temp,gm.startI,gm.startJ));
            blockList.add(temp);
        }
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
    public void setGameOver(){
        gameOver = true;
    }

}
