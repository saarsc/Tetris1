package com.tetris.saar.tetris;

import java.util.Date;
import java.util.Random;
import java.util.logging.Handler;

/**
 * Created by user on 09/08/2017.
 */

public class GameManger{
    int[][] board;
    GameActivity gameActivity ;
    public static final int startI = 5;
    public static final int startJ = 1;
    android.os.Handler uiHandler;
    static int dropSpeed = 100;
    public int getDropSpeed;

    public GameManger(GameActivity contex){
        this.board = new int [10][24];
        this.gameActivity = contex;
        uiHandler = new android.os.Handler();
        init();

    }

    public void init(){
        for(int i=0; i< this.board.length; i++){
            for (int j=0; j< this.board[i].length; j++){
                this.board[i][j] = 0;
            }
        }

    }
    public static int getDropSpeed() {
        return dropSpeed;
    }

    public static void setDropSpeed(int dropSpeed) {
        GameManger.dropSpeed = dropSpeed;
    }
    public boolean isEmptyDown(Blocks currentBlock){
        int id = currentBlock.getId();
        int[] place = currentBlock.getPlace();
        boolean empty = true;
        //Checking if reached the bottom
        //----------------------------------------
        //If there is low block
        if(currentBlock.isDown() || currentBlock.isDownLeft() || currentBlock.isDownRight()){
            if(place[1] ==22){
                empty = false;
            }
            else{
                if(currentBlock.isDown()){
                    if(this.board[place[0]][place[1]+2] != 0){
                        empty =false;
                    }

                }
                if(currentBlock.isDownLeft()){
                    if(this.board[place[0]-1][place[1]+2] != 0){
                        empty =false;
                    }
                }
                if(currentBlock.isDownRight()){
                    if(this.board[place[0]+1][place[1]+2] != 0){
                        empty =false;
                    }
                }
            }
        }else{
            if(place[1] == 23){
                empty = false;
            }else{
                //Under center
                if(this.board[place[0]][place[1]+1] != 0){
                    empty =false;
                }
                //Checking under left
                if(currentBlock.isLeft()) {
                    if(this.board[place[0]-1][place[1]+1] != 0){
                        empty =false;
                    }
                }else if(currentBlock.isLeftUp()){
                    if(this.board[place[0]-1][place[1]]!=0){
                        empty =false;
                    }
                }
                //Checking under right
                if(currentBlock.isRight()){
                    if(this.board[place[0]+1][place[1]+1] != 0){
                        empty =false;
                    }

                }else
                //Checking under the top right
                if( currentBlock.isRightUp() && this.board[place[0]+1][place[1]] !=0){
                    empty =false;
                }

            }
        }
        if((currentBlock.isDown() || currentBlock.isDownLeft() || currentBlock.isDownRight()) && place[1] == 22){

        }
        //If there is no down blocks

        //------------------------------------
        //Checking if there is a block under the block
        //----------------------------------------------
        //If there are down blocks
        if(empty == false){
            currentBlock.setMoving(false);
            return false;
        }
        return true;
    }
    //Move down the block
    public void moveDown(Blocks currentBlock) {
        int id = currentBlock.getId();
        int[] place = currentBlock.getPlace();
        boolean empty = true;

        if (place[1] <22){
            if(currentBlock.isDown()) {
                //Moving down
                this.board[place[0]][place[1] + 2] = this.board[place[0]][place[1] + 1];
            }
            if(currentBlock.isDownLeft()) {
                //Moving the left down corner
                this.board[place[0] - 1][place[1] + 2] = this.board[place[0] - 1][place[1] + 1];
                if (!currentBlock.isLeft()) {
                    this.board[place[0] - 1][place[1] + 1] = 0;
                }
            }

        //Moving the right down corner
            if(currentBlock.isDownRight()){
                this.board[place[0] + 1][place[1] + 2] = this.board[place[0] + 1][place[1] + 1];
                if (!currentBlock.isRight()) {
                    this.board[place[0] + 1][place[1] + 1] = 0;
                }
            }

    }
            //Moving center
            this.board[place[0]][place[1]+1] = this.board[place[0]][place[1]];
            if(!currentBlock.isUp()){
                this.board[place[0]][place[1]-1] = 0;
            }
            //Moving left
            this.board[place[0]-1][place[1]+1] = this.board[place[0]-1][place[1]];
            if(!currentBlock.isLeftUp()){
                this.board[place[0]-1][place[1]] = 0;
            }
            //Moving right
        if(currentBlock.isRight()) {
            this.board[place[0] + 1][place[1] + 1] = this.board[place[0] + 1][place[1]];
            if (!currentBlock.isRightUp()) {
                this.board[place[0] + 1][place[1]] = 0;
            }
        }
            //Moving up
            this.board[place[0]][place[1]] = this.board[place[0]][place[1] - 1];
            this.board[place[0]][place[1] - 1] = 0;
            //Moving the left up corner
            this.board[place[0]-1][place[1]] = this.board[place[0]-1][place[1]-1];
            this.board[place[0]-1][place[1]-1]=0;
            //Moving the right up corner
            this.board[place[0]+1][place[1]] = this.board[place[0]+1][place[1]-1];
            this.board[place[0]+1][place[1]-1]=0;

            //Setting new spot
            currentBlock.setPlace(place[0],place[1]+1);


    }
    //Generate a new block based on a random number
    public Blocks pickBlock(){
        Random rnd = new Random();
        int pick = rnd.nextInt(7)+1;
        Blocks newBlock = null;
        if(pick ==1){
             newBlock =new Squre(startI,startJ);
        }
        if(pick ==2 ){
             newBlock = new LineAndUpRight(startI,startJ);
        }
        if(pick ==3){
            newBlock = new LineAndUpLeft(startI,startJ);
        }
        if(pick==4){
            newBlock = new Line(startI,startJ);
        }
        if(pick==5){
            newBlock = new ZShaped(startI,startJ);
        }
        if(pick==6){
            newBlock = new LineAndMiddle(startI,startJ);
        }
        if(pick==7){
            newBlock = new SShaped(startI,startJ);
        }

        insertBlock(newBlock);
        return newBlock;

    }
    //Insert the block in the right place and place all the blocks around the center block
    public void insertBlock(Blocks newBlock){
        int id = newBlock.getId();
        int[] currentPlace = newBlock.getPlace();
        this.board[currentPlace[0]][currentPlace[1]] = id;
        if(newBlock.isUp()){
            this.board[currentPlace[0]][currentPlace[1]-1] = id;
        }
        if(newBlock.isLeft()){
            this.board[currentPlace[0]-1][currentPlace[1]]= id;
        }
        if(newBlock.isRight()){
            this.board[currentPlace[0]+1][currentPlace[1]]= id;
        }
        if(newBlock.isLeftUp()){
            this.board[currentPlace[0]-1][currentPlace[1]-1]= id;
        }
        if(newBlock.isRightUp()){
            this.board[currentPlace[0]+1][currentPlace[1]-1]= id;
        }
        if(newBlock.isDown()){
            this.board[currentPlace[0]][currentPlace[1]+1]= id;
        }
        if(newBlock.isDownLeft()){
            this.board[currentPlace[0]-1][currentPlace[1]+1]= id;
        }
        if(newBlock.isDownRight()){
            this.board[currentPlace[0]+1][currentPlace[1]+1]= id;
        }
        /* Special case for straight line */
     /*   if(id == 4){
            if(newBlock.isLeft()){
                this.board[currentPlace[0]-2][currentPlace[1]]= id;
            }
            if(newBlock.isDown()){
                this.board[currentPlace[0]][currentPlace[1]+2]= id;
            }
        }*/
    }
    public void removeBlock(Blocks block){
        int[] place = block.getPlace();
        if(block.isLeft()){
            this.board[place[0]-1][place[1]] =0;
        }
        if(block.isUp()){
            this.board[place[0]][place[1]-1] = 0;
        }
        if(block.isRight()){
            this.board[place[0]+1][place[1]] = 0;
        }
        if(block.isLeftUp()){
            this.board[place[0]-1][place[1]-1] = 0;
        }
        if(block.isRightUp()){
            this.board[place[0]+1][place[1]-1] = 0;
        }
        if(block.isDown()){
            this.board[place[0]][place[1]+1] = 0;
        }
        if(block.isDownLeft()){
            this.board[place[0]-1][place[1]+1] = 0;
        }
        if(block.isDownRight()){
            this.board[place[0]+1][place[1]+1] = 0;
        }
    }
    public void change(final Blocks currentBlock){
       final Blocks temp = currentBlock;
        removeBlock(currentBlock);
        currentBlock.changeRot();
        insertBlock(currentBlock);
       uiHandler.post(new Runnable() {
           @Override
           public void run() {
               if (currentBlock.isDown()) {
                   gameActivity.changeText(temp.getRotation() + "");
               }
           }
       });
    }
    public boolean emptyRot(Blocks currentBlock){
        int[] place = currentBlock.getPlace();
        if(currentBlock.isLeftUp()){
            if(this.board[place[0]+1][place[1]-1] != 0){
                return false;
            }
        }
        if(currentBlock.isUp()){
            if(this.board[place[0] +1][place[1]] != 0){
                return false;
            }
        }
        if(currentBlock.isRightUp()){
            if(this.board[place[0]+1][place[1] +1] != 0){
                return false;
            }
        }
        if(currentBlock.isRight()){
            if(this.board[place[0]][place[1]+1] != 0){
                return false;
            }
        }
        if(currentBlock.isDownRight()){
            if(this.board[place[0]-1][place[1]+1] != 0){
                return false;
            }
        }
        if(currentBlock.isDown()){
            if(this.board[place[0]-1][place[1]]!=0){
                return false;
            }
        }
        if(currentBlock.isDownRight()){
            if(this.board[place[0]-1][place[1]-1]!= 0){
                return false;
            }
        }
     return true;
    }
    //Return the board so you can display it
    public int[][] getDisplay(){
        return this.board;
    }
}
/*TODO
* Add special cases for id==4(line, move all around , insert block, remove )
* Update the speed to 1000
* Reason for win / lose
* move right and left (and all the checks)
* add the rest of the blocks
* do the layout
* what needs to happen when a row is filled
* rotations to all of the blocks
* */

