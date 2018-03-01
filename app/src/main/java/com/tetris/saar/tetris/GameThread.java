package com.tetris.saar.tetris;

import android.os.Handler;

import java.util.ArrayList;

/**
 * Created by user on 27/08/2017.
 */
//Special thread to control each block
public class GameThread extends Thread {
    /**
     * The gameManger.
     */
    GameManger gm;
    /**
     * The Ui handle.
     */
    Handler uiHandle;
    /**
     * For changing the rotation.
     */
    static boolean change =false;
    /**
     * For moving right.
     */
    static boolean right = false;
    /**
     * For moving left.
     */
    static  boolean left =false;
    /**
     * Pause and unpause the game.
     */
    static boolean pause = false;

    /**
     * The Block list.
     */
    ArrayList<Blocks> blockList = new ArrayList<>(); // Holds the next 50 blocks

    /**
     * Instantiates a new Game thread.
     *
     * @param gm the gm
     */
    public GameThread(GameManger gm){
        this.gm = gm;
        uiHandle = new Handler(); //For displaying on screen while being on a different thread
    }

    //The main game
    @Override
    public void run() {
        init();
        Blocks currentBlock = blockList.get(0);
        //While the game is not over
        do {
            if(!pause) {
                gm.landBlockBugFix();
                currentBlock = blockList.get(0);
                blockList.remove(0);
                //if the list is empty create the next 50 blocks
                if (blockList.isEmpty()) {
                    init();
                }
                //Holds the next block
                final Blocks finalNextBlock = blockList.get(0);
                //Displaying the next block
                uiHandle.post(new Runnable() {
                    @Override
                    public void run() {
                        gm.gameActivity.displayNextBlock(finalNextBlock);
                    }
                });
                gm.insertBlock(currentBlock); //Inserting the new block
                gm.setEmptySpaceBlockPos(currentBlock.getNextBlock()); //Setting the position of the landing block
                gm.insertBlock(currentBlock.getNextBlock()); //Inserting the landing block
                gm.moveDown(currentBlock); //Moving down the block
                //Loop that control each block
                while (currentBlock.isMoving()) {
                    //Handle the UI update(can not be done in different Threads)
                    update();
                    //Game ticks
                    try {
                        Thread.sleep(GameManger.getDropSpeed());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //Moving down
                    if (!pause) {
                        gm.moveDown(currentBlock);
                    }
                    //Changing the rotations
                    if (currentBlock.isMoving() && change) {
                        gm.change(currentBlock);
                        change = false;
                    }
                    //Moving right
                    if (currentBlock.isMoving() && right) {
                        gm.moveRight(currentBlock);
//                        update();
                        right = false;
                    }
                    //Moving left
                    if (currentBlock.isMoving() && left) {
                        gm.moveLeft(currentBlock);
//                        update();
                        left = false;
                    }
                }
                //Inseting the block at the end to fix disapeering blocks
                gm.insertBlock(currentBlock);
                update(); //Update to show the new block
                gm.setDropSpeed(250); //Changing the drop speed
                gm.addBlock(currentBlock); //Adding the block to the list
                gm.bugFixEmptyRow(currentBlock); //Checking if the empty row bug happened
                gm.checkBoard(); //Checking for full rows
            }
        } while (!pause &&(!gm.endOfGame(currentBlock)));
    }

    /**
     * Creating a list of blocks to use.
     */
    public void init(){

        for(int i=0; i< 50; i++){
            Blocks temp = gm.pickBlock();
            //temp.setNextBlock(new Blocks(temp,gm.startI,gm.startJ));
            blockList.add(temp);
        }
    }
    public void vUpdate(){
        uiHandle.post(new Runnable() {
            @Override
            public void run() {
                gm.gameActivity.vSync();
            }
        });
    }
    /**
     * Update the screen.
     */
    public void update(){
        uiHandle.post(new Runnable() {
            @Override
            public void run() {
                gm.gameActivity.toDisplay();
            }
        });
    }

    /**
     * Change speed.
     */
    public void changeSpeed(){
        GameManger.setDropSpeed(10);
    }

    /**
     * Rotation need to happen.
     */

    public void needToChange(){
        change = true;
    }

    /**
     * Move right.
     */
    public void moveRight(){
        right = true;
    }

    /**
     * Move left.
     */
    public void moveLeft(){
        left=true;
    }

    /**
     * Toggle pausing the game.
     */

    public void pause(){
        pause = true;
    }
    public  void unPause(){
        pause = false;
    }
}
