package com.tetris.saar.tetris;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.logging.Handler;

/**
 * Created by user on 09/08/2017.
 */

public class GameManger{
    //Blocks starting position
    public static final int startI = 5;
    public static final int startJ = 1;
    static int dropSpeed = 250; //Control's the dop speed of the block / Game ticks
    int[][] board; //Main game board
    int score; //The game score
    GameActivity gameActivity ; // Game activity control
    android.os.Handler uiHandler; //Handler to update the screen from the thread
    //public int getDropSpeed;
    ArrayList<int[]> poistionHistory; //Hold the main block end position
    ArrayList<Blocks> blocksHistory; //Hold all the blocks which was spawned at the current game
    //Constructor
    public GameManger(GameActivity contex){
        this.board = new int [10][24];
        this.gameActivity = contex;
        uiHandler = new android.os.Handler();
        init();
        this.poistionHistory = new ArrayList<>();
        this.blocksHistory =new ArrayList();
        this.score =0;
    }

    //Returning the drop speed
    public static int getDropSpeed() {
        return dropSpeed;
    }

    //Setting the drop speed
    public static void setDropSpeed(int dropSpeed) {
        GameManger.dropSpeed = dropSpeed;
    }

    //Creating all the slots in the array
    public void init(){
        for(int i=0; i< this.board.length; i++){
            for (int j=0; j< this.board[i].length; j++){
                this.board[i][j] = 0;
            }
        }

    }

    //Checking if a block can go down
    public boolean isEmptyDown(Blocks currentBlock) {
        int id = currentBlock.getId();
        int[] place = currentBlock.getPlace();
        boolean empty = true;
        //If the block as any low row blocks
        if (currentBlock.isDown() || currentBlock.isDownLeft() || currentBlock.isDownRight()) {
            //If at the end of the board
            if (place[1] == 22) {
                empty = false;
            } else {
                //Checking specific low row block
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
            //If it doesn't have any low row blocks
            //Check for end of board
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
        //Setting the movement of the block to false for it to stop and returning false for not empty
        if(empty == false){
            currentBlock.setMoving(false);
            return false;
        }
        return true;
    }
    //Add the end block position to the array
    public void addPoistion(int[] temp){
        poistionHistory.add(temp);
    }
    //Checking if the position holds the block
    public boolean realBlock(int i, int j){
        if(poistionHistory.contains(new int[i][j])){
            return true;
        }
        return  false;
    }
    //Adding new block
    public void addBlock(Blocks block){
        this.blocksHistory.add(block);
    }
    //Converting position into block
    public boolean fromPosToBlock(final int i, final int j){
       // return this.blocksHistory.get(this.poistionHistory.indexOf(new int[i][j]));
        return true;
    }
    //Move down the block
    public void moveDown(Blocks currentBlock) {
        int id = currentBlock.getId();
        int[] place = currentBlock.getPlace();
        if(isEmptyDown(currentBlock)){
            removeBlock(currentBlock);
            currentBlock.setPlace(place[0],place[1]+1);
            this.board[place[0]][place[1]-1] =0;
            insertBlock(currentBlock);
        }
  /*      //Checking if you can check for the button row
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
            currentBlock.setPlace(place[0],place[1]+1);*/

    }
    //Generate a new block based on a random number
    public Blocks pickBlock(){
        Random rnd = new Random();
        int pick = rnd.nextInt(7)+1;
        Blocks newBlock = null;

            if (pick == 1) {
                newBlock = new Squre(startI, startJ);
            }
            if (pick == 2) {
                newBlock = new LineAndUpRight(startI, startJ);
            }
            if (pick == 3) {
                newBlock = new LineAndUpLeft(startI, startJ);
            }
            if (pick == 4) {
                newBlock = new Line(startI, startJ);
            }
            if (pick == 5) {
                newBlock = new ZShaped(startI, startJ);
            }
            if (pick == 6) {
                newBlock = new LineAndMiddle(startI, startJ);
            }
            if (pick == 7) {
                newBlock = new SShaped(startI, startJ);

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
    //Removing a given block from the board
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
    //Moving right
    public void moveRight(Blocks currentBlock)  {
        int[] place = currentBlock.getPlace();
        if(emptyRight(currentBlock)){
            removeBlock(currentBlock); //Removing the block from the old position
            currentBlock.setPlace(place[0]+1,place[1]); // Setting a new place for the main block
            this.board[place[0]-1][place[1]] =0; // Removing the main block
            insertBlock(currentBlock); //Inserting the new block
        }
    }
    //Checking the block can move right
    public boolean emptyRight(Blocks currentBlock) {
        int[] place = currentBlock.getPlace();
        //If any block on the right column
        if (currentBlock.isRightUp() || currentBlock.isRight() || currentBlock.isDownRight()) {
            //If at the right edge of the board
            if (place[0] >= 8) {
                return false;
            } else {
                //Checking for Specific empty block
                //Right block
                if (currentBlock.isRight() && this.board[place[0] + 2][place[1]] != 0) {
                    return false;
                }
                //Top right block
                if (currentBlock.isRightUp() && this.board[place[0] + 2][place[1] - 1] != 0) {
                    return false;
                }
                //Down right block
                if (currentBlock.isDownRight() && this.board[place[0] + 2][place[1] + 1] != 0) {
                    return false;
                }
            }
        }
        //If doesn't have any right column
        //Checking for right edge of the board
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
            //Down
            if (currentBlock.isDown() && this.board[place[0] + 1][place[1] + 1] != 0 && !currentBlock.isDownRight()) {
                return false;
            }
            return true;
        }
    }

    //Fixing a bug where a block just stops mid air and won't go down
    public void bugFixEmptyRow(Blocks currentBlock){
        int[] place = currentBlock.getPlace();
        boolean check= true;
        //If it's not at the bottom row
        if(place[1] < 23){
            //Checking for the empty block
            if(this.board[place[0]][place[1]+1] != 0){
                check =false;
            }
            //Checking if it's not on the right edge of the screen + empty down right
            if(place[0]!= 9) {
                if (this.board[place[0]+1][place[1]+1] != 0){
                    check = false;
                }
            }
            //Checking if it's not at the left side of the screen + empty down left
            if(place[0] !=0) {
                if (this.board[place[0] - 1][place[1] + 1] != 0) {
                    check = false;
                }
            }
            //If the bug exist move the block down once more
            if(check){
                if(isEmptyDown(currentBlock)){
                    moveDown(currentBlock);
                }
            }
        }
    }
    //Checking if the block can rotate
    public boolean emptyRot(Blocks currentBlock){
        int[] place = currentBlock.getPlace();
        boolean rot  = true;
        //Checking for the up blocks
        if(currentBlock.isLeftUp()|| currentBlock.isUp() || currentBlock.isRightUp()){
            if(place[0] == 9){
                rot = false;
            }else {
                if (currentBlock.isLeftUp()) {
                    if (this.board[place[0] + 1][place[1] - 1] != 0 && !currentBlock.isRightUp()) {
                        rot = false;
                    }
                }
                if (currentBlock.isUp()) {
                    if (this.board[place[0] + 1][place[1]] != 0 && !currentBlock.isRight()) {
                        rot = false;
                    }
                }
                if (currentBlock.isRightUp()) {
                    if (place[1] < 23 && this.board[place[0] + 1][place[1] + 1] != 0 && !currentBlock.isDownRight()) {
                        rot = false;
                    }
                }
            }
        }
        //Checking for the right blocks
        if(currentBlock.isRight() || currentBlock.isDownRight()){
            if(place[1]==23){
                rot = false;
            }else{
                if(currentBlock.isRight()){
                    if(this.board[place[0]][place[1]+1]!=0 && !currentBlock.isDown()){
                        rot= false;
                    }
                }
                if(currentBlock.isDownRight()){
                    if(place[0]> 0&&this.board[place[0]-1][place[1]+1] != 0 && !currentBlock.isDownLeft()){
                        rot = false;
                    }
                }
            }
        }
        //Checking for the down blocks
        if(currentBlock.isDown() || currentBlock.isDownLeft()){
            if(place[0] == 0){
                rot = false;
            }else{
                if(currentBlock.isDown()){
                    if(this.board[place[0]-1][place[1]]!= 0 && !currentBlock.isLeft()){
                        rot = false;
                    }
                }
                if(currentBlock.isDownLeft()){
                    if(place[1] > 0&&this.board[place[0]-1][place[1]-1] != 0 && !currentBlock.isLeftUp()){
                        rot = false;
                    }
                }
            }
        }
        //Checking for the left block
        if(currentBlock.isLeft()){
            if(place[1]==0){
                rot = false;
            }else{
                if(this.board[place[0]][place[1]-1]!= 0 && !currentBlock.isUp()){
                    rot = false;
                }
            }
        }
  /*      //Checking for the left up block
        if(currentBlock.isLeftUp() &&place[0]< 9 && !currentBlock.isRightUp() && place[1] ==0){
            if(this.board[place[0]+1][place[1]-1] != 0){
                return false;
            }
        }
        //Checking for the up block
        if(currentBlock.isUp()&&place[0]< 9 &&!currentBlock.isRight()){
            if(this.board[place[0] +1][place[1]] != 0){
                return false;
            }
        }
        //Checking for the right up block
        if(currentBlock.isRightUp() &&place[0]< 9 && place[1] < 23  &&!currentBlock.isDownRight()){
            if(this.board[place[0]+1][place[1] +1] != 0){
                return false;
            }
        }
        //Checking for the right block
        if(currentBlock.isRight() && place[1] < 23 &&!currentBlock.isDown()){
            if(this.board[place[0]][place[1]+1] != 0){
                return false;
            }
        }
        //Checking for the down right down block
        if(currentBlock.isDownRight() && place[1] < 23 && place[0] > 0  &&!currentBlock.isDownLeft()){
            if(this.board[place[0]-1][place[1]+1] != 0){
                return false;
            }
        }
        //Checking for the down block
        if(currentBlock.isDown() & place[0] > 0  &&!currentBlock.isLeft()){
            if(this.board[place[0]-1][place[1]]!=0){
                return false;
            }
        }
        //Checking for the down left block
        if(currentBlock.isDownLeft() & place[0] > 0 && place[1] ==0 &&!currentBlock.isLeftUp()){
            if(this.board[place[0]-1][place[1]-1]!= 0){
                return false;
            }
        }
        //Checking for the left block
        if(currentBlock.isLeft() && place[1] ==0){
            return false;
        }*/
     return rot;
    }
    //Checking if can move left
    public boolean emptyLeft(Blocks currentBlock) {
        int[] place = currentBlock.getPlace();
        //If any left column
        if (currentBlock.isLeftUp() || currentBlock.isLeft() || currentBlock.isDownLeft()) {
            //If on the left edge of the screen
            if (place[0] <= 1) {
                return false;
            } else {
                //Checking for a specific left block
                //Left block
                if (currentBlock.isLeft() && this.board[place[0] - 2][place[1]] != 0) {
                    return false;
                }
                //Left up block
                if (currentBlock.isLeftUp() && this.board[place[0] - 2][place[1] - 1] != 0) {
                    return false;
                }
                //Down left block
                if (currentBlock.isDownLeft() && this.board[place[0] - 2][place[1] + 1] != 0) {
                    return false;
                }
            }
        }
        //If on the edge of the board
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
            //Down
            if (currentBlock.isDown() && this.board[place[0] - 1][place[1] + 1] != 0 && !currentBlock.isDownLeft()) {
                return false;
            }
        }
        return true;
    }
    //Move left
    public void moveLeft(Blocks currentBlock){
        int[] place = currentBlock.getPlace();
        if(emptyLeft(currentBlock)) {
            removeBlock(currentBlock); //Removing the block from the current place
            currentBlock.setPlace(place[0] - 1, place[1]); //Setting the place of the block at the new point
            this.board[place[0] + 1][place[1]] = 0; // Removing the old block from the old position
            insertBlock(currentBlock); // Inserting the block at the new place
        }
    }
    /*public boolean endOfGame(){
     int[] lastPos=  poistionHistory.get(poistionHistory.size()-1);
        if (lastPos[1] ==0 || lastPos[1] ==1){
            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    gameActivity.gameOver();
                }
            });
            return  true;
        }
        return false;
    }*/
    public boolean endOfGame(Blocks curenntBlock){
        int[] lastPos=  blocksHistory.get(blocksHistory.size()-1).getPlace();
        if(curenntBlock.isUp() || curenntBlock.isLeftUp() || curenntBlock.isRightUp()){
            if (lastPos[1] == startJ +1&& !curenntBlock.isMoving()) {
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        gameActivity.gameOver();
                    }
                });
                return true;
            }
        }
        else {
            if (lastPos[1] == startJ && !curenntBlock.isMoving()) {
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        gameActivity.gameOver();
                    }
                });
                return true;
            }
        }
        return false;
    }
    //Checking if there are any full rows
    public void checkBoard(){
        boolean moveDown = true;
        boolean checkAgain = false;
        int atOnce =1;
        for(int j= board[0].length -1; j>=2; j--){
            for(int i =0; i<board.length; i++){
                if(board[i][j] == 0){
                    moveDown = false;
                }
            }
            if(moveDown){
                pushDown(j);
                if(atOnce ==1 ){
                    this.score += 40 * ((23-j)+1);
                }
                if(atOnce == 2){
                    this.score += 100 * ((23-j)+1);
                }
                if(atOnce == 3){
                    this.score += 300 * ((23-j)+1);
                }
                if(atOnce >= 4){
                    this.score += 1200 * ((23-j)+1);
                }
                //this.score += 100;
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        gameActivity.changeScore(score);
                    }
                });
                checkAgain =true;
                atOnce++;
            }
            moveDown = true;
        }
        if(checkAgain){
            checkBoard();
        }
    }
    //Moving all the rows down and clearing the full row
    public void pushDown(int j){
        for(int i=0;i< this.board.length; i++){
            for(int h=j;1<=h;h--){
             /*   if(realBlock(i,h)){

                }else {
                    if (fromPosToBlock(i, h)) {
                        this.board[h][h - 1] = 0;
                    }
                }*/
                this.board[i][h] = this.board[i][h-1];
            }
        }
    }
    //Return the board so you can display it
    public int[][] getDisplay(){
        return this.board;
    }
}

/*TODO
Need to finish layout of the main game

Add the line condition

Pause menu?

End of game stuff
* */

