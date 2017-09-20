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
    static int dropSpeed = 500;
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
    public boolean isEmptyDown(Blocks currentBlock) {
        int id = currentBlock.getId();
        int[] place = currentBlock.getPlace();
        boolean empty = true;
        //Checking if reached the bottom
        //----------------------------------------
        if (currentBlock.isDown() || currentBlock.isDownLeft() || currentBlock.isDownRight()) {
            if (place[1] == 22) {
                empty = false;
            } else {
                if (currentBlock.isDown()) {
                    if (this.board[place[0]][place[1] + 2] != 0) {
                        empty = false;
                    }

                }
                if (currentBlock.isDownLeft()) {
                    if (this.board[place[0] - 1][place[1] + 2] != 0) {
                        empty = false;
                    }
                }
                if (currentBlock.isDownRight()) {
                    if (this.board[place[0] + 1][place[1] + 2] != 0) {
                        empty = false;
                    }
                }
            }
        }

            if (place[1] == 23) {
                empty = false;
            } else {
                //Under center
                if (this.board[place[0]][place[1] + 1] != 0 && !currentBlock.isDown()) {
                    empty = false;
                }
                //Checking under left
                if (currentBlock.isLeft() && !currentBlock.isDownLeft()) {
                    if (this.board[place[0] - 1][place[1] + 1] != 0) {
                        empty = false;
                    }
                } else if (currentBlock.isLeftUp()) {
                    if (this.board[place[0] - 1][place[1]] != 0) {
                        empty = false;
                    }
                }
                //Checking under right
                if (currentBlock.isRight() && !currentBlock.isDownRight()) {
                    if (this.board[place[0] + 1][place[1] + 1] != 0) {
                        empty = false;
                    }

                } else
                    //Checking under the top right
                    if (currentBlock.isRightUp() && this.board[place[0] + 1][place[1]] != 0) {
                        empty = false;
                    }

            }


        /*if((currentBlock.isDown() || currentBlock.isDownLeft() || currentBlock.isDownRight()) && place[1] == 22){

        }*/
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
        if(currentBlock.isLeft()) {
            this.board[place[0] - 1][place[1] + 1] = this.board[place[0] - 1][place[1]];
            if (!currentBlock.isLeftUp()) {
                this.board[place[0] - 1][place[1]] = 0;
            }
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
        if(currentBlock.isLeftUp()) {
            this.board[place[0] - 1][place[1]] = this.board[place[0] - 1][place[1] - 1];
            this.board[place[0] - 1][place[1] - 1] = 0;
        }
            //Moving the right up corner
        if(currentBlock.isRightUp()) {
            this.board[place[0] + 1][place[1]] = this.board[place[0] + 1][place[1] - 1];
            this.board[place[0] + 1][place[1] - 1] = 0;
        }
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

        if(place[0] != 0){
            if(block.isLeft()){
                this.board[place[0]-1][place[1]] =0;
            }
            if(block.isLeftUp()){
                this.board[place[0]-1][place[1]-1] = 0;
            }
            if(block.isDownLeft()){
                this.board[place[0]-1][place[1]+1] = 0;
            }
        }
        if(block.isUp()){
            this.board[place[0]][place[1]-1] = 0;
        }
        if(place[0] != 9){
            if(block.isRight()){
                this.board[place[0]+1][place[1]] = 0;
            }
            if(block.isRightUp()){
                this.board[place[0]+1][place[1]-1] = 0;
            }
            if(block.isDownRight()){
                this.board[place[0]+1][place[1]+1] = 0;
            }
        }

        if(block.isDown()){
            this.board[place[0]][place[1]+1] = 0;
        }


    }
    //Rotation
    public void change(final Blocks currentBlock){
       final Blocks temp = currentBlock;
        if(emptyRot(currentBlock)) {
            removeBlock(currentBlock);
            currentBlock.changeRot();
            insertBlock(currentBlock);
        }
    }
    public void moveRight(Blocks currentBlock)  {
        int[] place = currentBlock.getPlace();
        if(emptyRight(currentBlock)){
            removeBlock(currentBlock);
            currentBlock.setPlace(place[0]+1,place[1]);
            this.board[place[0]-1][place[1]] =0;
            insertBlock(currentBlock);
        }
    }
    public boolean emptyRight(Blocks currentBlock) {
        int[] place = currentBlock.getPlace();

        if (currentBlock.isRightUp() || currentBlock.isRight() || currentBlock.isDownRight()) {
            if (place[0] >= 8) {
                return false;
            } else {
                if (currentBlock.isRight() && this.board[place[0] + 2][place[1]] != 0) {
                    return false;
                }
                if (currentBlock.isRightUp() && this.board[place[0] + 2][place[1] - 1] != 0) {
                    return false;
                }
                if (currentBlock.isDownRight() && this.board[place[0] + 2][place[1] + 1] != 0) {
                    return false;
                }
            }
        }
        if (place[0] == 9) {
            return false;
        } else {
            //Up
            if (currentBlock.isUp() && !currentBlock.isRightUp() && this.board[place[0] + 1][place[1] - 1] != 0) {
                return false;
            }
            //Center
            if (this.board[place[0] + 1][place[1]] != 0 && !currentBlock.isRight()) {
                return false;
            }
            if (currentBlock.isDown() && this.board[place[0] + 1][place[1] + 1] != 0 && !currentBlock.isDownRight()) {
                return false;
            }
            return true;
        }
    }


    public void bugFixEmptyRow(Blocks currentBlock){
        int[] place = currentBlock.getPlace();
        boolean check= true;
     /*   if(place[1] < 23){
            if(this.board[place[0]][place[1]+1] == 0){
                if(place[0] !=9){
                    if(this.board[place[0]+1][place[1]+1] ==0){
                        if(isEmptyDown(currentBlock)){
                            moveDown(currentBlock);
                        }
                    }
                }else
                if(isEmptyDown(currentBlock)){
                    moveDown(currentBlock);
                }
            }
            if(place[0]!=0) {
                if (this.board[place[0] - 1][place[1] + 1] == 0) {
                    if()
                }
            }*/
        if(place[1] < 23){
            if(this.board[place[0]][place[1]+1] != 0){
                check =false;
            }
            if(place[0]!= 9) {
                if (this.board[place[0]+1][place[1]+1] != 0){
                    check = false;
                }
            }
            if(place[0] !=0) {
                if (this.board[place[0] - 1][place[1] + 1] != 0) {
                    check = false;
                }
            }
            if(check){
                if(isEmptyDown(currentBlock)){
                    moveDown(currentBlock);
                }
            }
        }


    }
    public boolean emptyRot(Blocks currentBlock){
        int[] place = currentBlock.getPlace();
        if(currentBlock.isLeftUp() &&place[0]< 9 && !currentBlock.isRightUp() && place[1] ==0){
            if(this.board[place[0]+1][place[1]-1] != 0){
                return false;
            }
        }
        if(currentBlock.isUp()&&place[0]< 9 &&!currentBlock.isRight()){
            if(this.board[place[0] +1][place[1]] != 0){
                return false;
            }
        }
        if(currentBlock.isRightUp() &&place[0]< 9 && place[1] < 23  &&!currentBlock.isDownRight()){
            if(this.board[place[0]+1][place[1] +1] != 0){
                return false;
            }
        }
        if(currentBlock.isRight() && place[1] < 23 &&!currentBlock.isDown()){
            if(this.board[place[0]][place[1]+1] != 0){
                return false;
            }
        }
        if(currentBlock.isDownRight() && place[1] < 23 && place[0] > 0  &&!currentBlock.isDownLeft()){
            if(this.board[place[0]-1][place[1]+1] != 0){
                return false;
            }
        }
        if(currentBlock.isDown() & place[0] > 0  &&!currentBlock.isLeft()){
            if(this.board[place[0]-1][place[1]]!=0){
                return false;
            }
        }
        if(currentBlock.isDownLeft() & place[0] > 0 && place[1] ==0 &&!currentBlock.isLeftUp()){
            if(this.board[place[0]-1][place[1]-1]!= 0){
                return false;
            }
        }
        if(currentBlock.isLeft() && place[1] ==0){
            return false;
        }

     return true;
    }
    public boolean emptyLeft(Blocks currentBlock) {
        int[] place = currentBlock.getPlace();
        if (currentBlock.isLeftUp() || currentBlock.isLeft() || currentBlock.isDownLeft()) {
            if (place[0] <= 1) {
                return false;
            } else {
                if (currentBlock.isLeft() && this.board[place[0] - 2][place[1]] != 0) {
                    return false;
                }
                if (currentBlock.isLeftUp() && this.board[place[0] - 2][place[1] - 1] != 0) {
                    return false;
                }
                if (currentBlock.isDownLeft() && this.board[place[0] - 2][place[1] + 1] != 0) {
                    return false;
                }
            }
        }
        if(place[0] == 0 ){
            return false;
        }else{
            //Up
            if (currentBlock.isUp() && !currentBlock.isLeftUp() && this.board[place[0] - 1][place[1] - 1] != 0) {
                return false;
            }
            //Center
            if (this.board[place[0] - 1][place[1]] != 0 && !currentBlock.isLeft()) {
                return false;
            }
            if (currentBlock.isDown() && this.board[place[0] - 1][place[1] + 1] != 0 && !currentBlock.isDownLeft()) {
                return false;
            }
        }

        return true;
    }
    public void moveLeft(Blocks currentBlock){
        int[] place = currentBlock.getPlace();
        if(emptyLeft(currentBlock)) {
            removeBlock(currentBlock);
            currentBlock.setPlace(place[0] - 1, place[1]);
            this.board[place[0] + 1][place[1]] = 0;
            insertBlock(currentBlock);
        }
    }
    public void checkBoard(){
        int rowId =0;
        boolean moveDown = true;
        for(int j= board[0].length -1; j>=2; j--){
            for(int i = board.length -1; i>=0; i--){
                if(j==0){
                    rowId = board[i][j];
                }
                if(board[i][j] != rowId){
                    moveDown = false;
                }
            }
            if(moveDown){
                pushDown(j);
            }
        }
    }
    public void pushDown(int j){
        for(int i=0;i< this.board.length; i++){
            for(int h=j;0<h;h--){
                this.board[i][j] = this.board[i][j-1];
            }
        }
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
* do the layout
* what needs to happen when a row is filled
* All the other bullshit I need for this project which doesn't envolve the game
* */

