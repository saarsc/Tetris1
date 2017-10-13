package com.tetris.saar.tetris;

import java.util.ArrayList;
import java.util.Random;


public class GameManger {
    //Blocks starting position
    public static final int startI = 5;
    public static final int startJ = 1;
    static int dropSpeed = 250; //Control's the dop speed of the block / Game ticks
    int[][] board; //Main game board
    int score; //The game score
    GameActivity gameActivity; // Game activity control
    android.os.Handler uiHandler; //Handler to update the screen from the thread
    //public int getDropSpeed;
    ArrayList<int[]> poistionHistory; //Hold the main block end position
    ArrayList<Blocks> blocksHistory; //Hold all the blocks which was spawned at the current game
    //Thread for the land block
    Runnable run;
    Thread thread;
    LandBLockThread lb;
    //Constructor
    public GameManger(GameActivity contex) {
        this.board = new int[10][24];
        this.gameActivity = contex;
        uiHandler = new android.os.Handler();
        init();
        this.poistionHistory = new ArrayList<>();
        this.blocksHistory = new ArrayList();
        this.score = 0;
        run = new LandBLockThread(this);
        thread = new Thread(run);
        thread.start();
        lb = new LandBLockThread(this);
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
    private void init() {
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                this.board[i][j] = 0;
            }
        }

    }

    //Checking if a block can go down
    private boolean isEmptyDown(Blocks currentBlock) {
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
                    if (this.board[place[0]][place[1] + 2] > 0 && this.board[place[0]][place[1] + 2] < 8) {
                        empty = false;
                    }

                }
                if (currentBlock.isDownLeft()) {
                    if (this.board[place[0] - 1][place[1] + 2] > 0 && this.board[place[0] - 1][place[1] + 2] < 8) {
                        empty = false;
                    }
                }
                if (currentBlock.isDownRight()) {
                    if (this.board[place[0] + 1][place[1] + 2] > 0 && this.board[place[0] + 1][place[1] + 2] < 8) {
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
            if (this.board[place[0]][place[1] + 1] > 0 && this.board[place[0]][place[1] + 1] < 8 && !currentBlock.isDown()) {
                empty = false;
            }
            //Checking under left
            if (currentBlock.isLeft() && !currentBlock.isDownLeft()) {
                if (this.board[place[0] - 1][place[1] + 1] > 0 && this.board[place[0] - 1][place[1] + 1] < 8) {
                    empty = false;
                }
            } else if (currentBlock.isLeftUp()) {
                if (this.board[place[0] - 1][place[1]] > 0 && this.board[place[0] - 1][place[1]] < 8) {
                    empty = false;
                }
            }
            //Checking under right
            if (currentBlock.isRight() && !currentBlock.isDownRight()) {
                if (this.board[place[0] + 1][place[1] + 1] > 0 && this.board[place[0] + 1][place[1] + 1] < 8) {
                    empty = false;
                }

            } else
                //Checking under the top right
                if (currentBlock.isRightUp() && this.board[place[0] + 1][place[1]] > 0 && this.board[place[0] + 1][place[1]] < 8) {
                    empty = false;
                }

        }
        //Setting the movement of the block to false for it to stop and returning false for not empty
        if (empty == false) {
            currentBlock.setMoving(false);
            return false;
        }
        return true;
    }

    //Add the end block position to the array
    public void addPoistion(int[] temp) {
        poistionHistory.add(temp);
    }
    //Adding new block
    public void addBlock(Blocks block) {
        this.blocksHistory.add(block);
    }

    //Converting position into block
    private Blocks fromPosToBlock(final int i, final int j) {
        if (this.poistionHistory.contains(new int[i][j])) {
            return this.blocksHistory.get(this.poistionHistory.indexOf(new int[i][j]));
        }
        return null;
    }
    //Move down the block
    public void moveDown(Blocks currentBlock) {
        int[] place = currentBlock.getPlace();
        if (isEmptyDown(currentBlock)) {
            removeBlock(currentBlock);
            currentBlock.setPlace(place[0], place[1] + 1);
            this.board[place[0]][place[1] - 1] = 0;
            insertBlock(currentBlock);
        }

    }

    //Generate a new block based on a random number
    public Blocks pickBlock() {
        Random rnd = new Random();
        int pick = rnd.nextInt(7) + 1;
        Blocks newBlock = null;

        if (pick == 1) {
            newBlock = new Squre(startI, startJ);
            newBlock.setNextBlock(new Squre((Squre) newBlock, 5, startJ));
        }
        if (pick == 2) {
            newBlock = new LineAndUpRight(startI, startJ);
            newBlock.setNextBlock(new LineAndUpRight((LineAndUpRight) newBlock, 5, startJ));
        }
        if (pick == 3) {
            newBlock = new LineAndUpLeft(startI, startJ);
            newBlock.setNextBlock(new LineAndUpLeft((LineAndUpLeft) newBlock, 5, startJ));
        }
        if (pick == 4) {
            newBlock = new Line(startI, startJ);
            newBlock.setNextBlock(new Line((Line) newBlock, 5, startJ));
        }
        if (pick == 5) {
            newBlock = new ZShaped(startI, startJ);
            newBlock.setNextBlock(new ZShaped((ZShaped) newBlock, 5, startJ));
        }
        if (pick == 6) {
            newBlock = new LineAndMiddle(startI, startJ);
            newBlock.setNextBlock(new LineAndMiddle((LineAndMiddle) newBlock, 5, startJ));
        }
        if (pick == 7) {
            newBlock = new SShaped(startI, startJ);
            newBlock.setNextBlock(new SShaped((SShaped) newBlock, 5, startJ));
        }
        return newBlock;

    }

    //Insert the block in the right place and place all the blocks around the center block
    public void insertBlock(Blocks newBlock) {
        int id = newBlock.getId();
        int[] currentPlace = newBlock.getPlace();
        this.board[currentPlace[0]][currentPlace[1]] = id;
        if (newBlock.isUp()) {
            this.board[currentPlace[0]][currentPlace[1] - 1] = id;
        }
        if (newBlock.isLeft()) {
            this.board[currentPlace[0] - 1][currentPlace[1]] = id;
        }
        if (newBlock.isRight()) {
            this.board[currentPlace[0] + 1][currentPlace[1]] = id;
        }
        if (newBlock.isLeftUp()) {
            this.board[currentPlace[0] - 1][currentPlace[1] - 1] = id;
        }
        if (newBlock.isRightUp()) {
            this.board[currentPlace[0] + 1][currentPlace[1] - 1] = id;
        }
        if (newBlock.isDown()) {
            this.board[currentPlace[0]][currentPlace[1] + 1] = id;
        }
        if (newBlock.isDownLeft()) {
            this.board[currentPlace[0] - 1][currentPlace[1] + 1] = id;
        }
        if (newBlock.isDownRight()) {
            this.board[currentPlace[0] + 1][currentPlace[1] + 1] = id;
        }
    }

    //Removing a given block from the board
    private void removeBlock(Blocks block) {
        int[] place = block.getPlace();
        if (place[0] != 0) {
            if (block.isLeft()) {
                this.board[place[0] - 1][place[1]] = 0;
            }
            if (block.isLeftUp()) {
                this.board[place[0] - 1][place[1] - 1] = 0;
            }
            if (block.isDownLeft()) {
                this.board[place[0] - 1][place[1] + 1] = 0;
            }
        }
        if (block.isUp()) {
            this.board[place[0]][place[1] - 1] = 0;
        }
        if (place[0] != 9) {
            if (block.isRight()) {
                this.board[place[0] + 1][place[1]] = 0;
            }
            if (block.isRightUp()) {
                this.board[place[0] + 1][place[1] - 1] = 0;
            }
            if (block.isDownRight()) {
                this.board[place[0] + 1][place[1] + 1] = 0;
            }
        }

        if (block.isDown()) {
            this.board[place[0]][place[1] + 1] = 0;
        }
    }

    //Rotation
    public void change(Blocks currentBlock) {
        int[] oldPlace = currentBlock.getNextBlock().getPlace();
        if (emptyRot(currentBlock)) {
            //Regular block
            removeBlock(currentBlock);
            currentBlock.changeRot();
            insertBlock(currentBlock);
            //Land block handle
            removeBlock(currentBlock.getNextBlock());
            this.board[oldPlace[0]][oldPlace[1]] = 0;
            insertBlock(lb.change(currentBlock,this.board));
        }
    }

    //Moving right
    public void moveRight(Blocks currentBlock) {
        int[] place = currentBlock.getPlace();
        int[] oldPlace = currentBlock.getNextBlock().getPlace();
        if (emptyRight(currentBlock)) {
            //Regular
            removeBlock(currentBlock); //Removing the block from the old position
            currentBlock.setPlace(place[0] + 1, place[1]); // Setting a new place for the main block
            this.board[place[0] - 1][place[1]] = 0; // Removing the main block
            insertBlock(currentBlock); //Inserting the new block
            //Land Block handle
            removeBlock(currentBlock.getNextBlock());
            this.board[oldPlace[0]][oldPlace[1]] = 0;
            insertBlock(lb.moveRight(currentBlock,this.board));
        }
    }
    //Checking the block can move right
    private boolean emptyRight(Blocks currentBlock) {
        int[] place = currentBlock.getPlace();
        //If any block on the right column
        if (currentBlock.isRightUp() || currentBlock.isRight() || currentBlock.isDownRight()) {
            //If at the right edge of the board
            if (place[0] >= 8) {
                return false;
            } else {
                //Checking for Specific empty block
                //Right block
                if (currentBlock.isRight() && this.board[place[0] + 2][place[1]] > 0 && this.board[place[0] + 2][place[1]] < 8) {
                    return false;
                }
                //Top right block
                if (currentBlock.isRightUp() && this.board[place[0] + 2][place[1] - 1] > 0 && this.board[place[0] + 2][place[1] - 1] < 8) {
                    return false;
                }
                //Down right block
                if (currentBlock.isDownRight() && this.board[place[0] + 2][place[1] + 1] > 0 && this.board[place[0] + 2][place[1] + 1] < 8) {
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
            if (currentBlock.isUp() && !currentBlock.isRightUp() && this.board[place[0] + 1][place[1] - 1] > 0 && this.board[place[0] + 1][place[1] - 1] < 8) {
                return false;
            }
            //Center
            if (this.board[place[0] + 1][place[1]] > 0 && this.board[place[0] + 1][place[1]] < 8 && !currentBlock.isRight()) {
                return false;
            }
            //Down
            return !(currentBlock.isDown() && this.board[place[0] + 1][place[1] + 1] > 0 && currentBlock.isDown() && this.board[place[0] + 1][place[1] + 1] < 8 && !currentBlock.isDownRight());
        }
    }

    //Fixing a bug where a block just stops mid air and won't go down
    public void bugFixEmptyRow(Blocks currentBlock) {
        int[] place = currentBlock.getPlace();
        boolean check = true;
        //If it's not at the bottom row
        if (place[1] < 23) {
            //Checking for the empty block
            if (this.board[place[0]][place[1] + 1] > 0 && this.board[place[0]][place[1] + 1] < 8) {
                check = false;
            }
            //Checking if it's not on the right edge of the screen + empty down right
            if (place[0] != 9) {
                if (this.board[place[0] + 1][place[1] + 1] > 0 && this.board[place[0] + 1][place[1] + 1] < 8) {
                    check = false;
                }
            }
            //Checking if it's not at the left side of the screen + empty down left
            if (place[0] != 0) {
                if (this.board[place[0] - 1][place[1] + 1] > 0 && this.board[place[0] - 1][place[1] + 1] < 8) {
                    check = false;
                }
            }
            //If the bug exist move the block down once more
            if (check) {
                if (isEmptyDown(currentBlock)) {
                    moveDown(currentBlock);
                }
            }
        }
    }

    //Checking if the block can rotate
    private boolean emptyRot(Blocks currentBlock) {
        int[] place = currentBlock.getPlace();
        boolean rot = true;
        //Checking for the up blocks
        if (currentBlock.isLeftUp() || currentBlock.isUp() || currentBlock.isRightUp()) {
            if (place[0] == 9) {
                rot = false;
            } else {
                if (currentBlock.isLeftUp()) {
                    if (this.board[place[0] + 1][place[1] - 1] > 0 && this.board[place[0] + 1][place[1] - 1] < 8 && !currentBlock.isRightUp()) {
                        rot = false;
                    }
                }
                if (currentBlock.isUp()) {
                    if (this.board[place[0] + 1][place[1]] > 0 && this.board[place[0] + 1][place[1]] < 8 && !currentBlock.isRight()) {
                        rot = false;
                    }
                }
                if (currentBlock.isRightUp()) {
                    if (place[1] < 23 && this.board[place[0] + 1][place[1] + 1] > 0 && place[1] < 23 && this.board[place[0] + 1][place[1] + 1] < 8 && !currentBlock.isDownRight()) {
                        rot = false;
                    }
                }
            }
        }
        //Checking for the right blocks
        if (currentBlock.isRight() || currentBlock.isDownRight()) {
            if (place[1] == 23) {
                rot = false;
            } else {
                if (currentBlock.isRight()) {
                    if (this.board[place[0]][place[1] + 1] > 0 && this.board[place[0]][place[1] + 1] < 8 && !currentBlock.isDown()) {
                        rot = false;
                    }
                }
                if (currentBlock.isDownRight()) {
                    if (place[0] > 0 && this.board[place[0] - 1][place[1] + 1] > 0 && this.board[place[0] - 1][place[1] + 1] < 8 && !currentBlock.isDownLeft()) {
                        rot = false;
                    }
                }
            }
        }
        //Checking for the down blocks
        if (currentBlock.isDown() || currentBlock.isDownLeft()) {
            if (place[0] == 0) {
                rot = false;
            } else {
                if (currentBlock.isDown()) {
                    if (this.board[place[0] - 1][place[1]] > 0 && this.board[place[0] - 1][place[1]] < 8 && !currentBlock.isLeft()) {
                        rot = false;
                    }
                }
                if (currentBlock.isDownLeft()) {
                    if (place[1] > 0 && this.board[place[0] - 1][place[1] - 1] > 0 && place[1] > 0 && this.board[place[0] - 1][place[1] - 1] < 8 && !currentBlock.isLeftUp()) {
                        rot = false;
                    }
                }
            }
        }
        //Checking for the left block
        if (currentBlock.isLeft()) {
            if (place[1] == 0) {
                rot = false;
            } else {
                if (this.board[place[0]][place[1] - 1] > 0 && this.board[place[0]][place[1] - 1] < 8 && !currentBlock.isUp()) {
                    rot = false;
                }
            }
        }
        return rot;
    }

    //Checking if can move left
    private boolean emptyLeft(Blocks currentBlock) {
        int[] place = currentBlock.getPlace();
        //If any left column
        if (currentBlock.isLeftUp() || currentBlock.isLeft() || currentBlock.isDownLeft()) {
            //If on the left edge of the screen
            if (place[0] <= 1) {
                return false;
            } else {
                //Checking for a specific left block
                //Left block
                if (currentBlock.isLeft() && this.board[place[0] - 2][place[1]] > 0 && this.board[place[0] - 2][place[1]] < 8) {
                    return false;
                }
                //Left up block
                if (currentBlock.isLeftUp() && this.board[place[0] - 2][place[1] - 1] > 0 && this.board[place[0] - 2][place[1] - 1] < 8) {
                    return false;
                }
                //Down left block
                if (currentBlock.isDownLeft() && this.board[place[0] - 2][place[1] + 1] > 0 && this.board[place[0] - 2][place[1] + 1] < 8) {
                    return false;
                }
            }
        }
        //If on the edge of the board
        if (place[0] == 0) {
            return false;
        } else {
            //Up
            if (currentBlock.isUp() && !currentBlock.isLeftUp() && this.board[place[0] - 1][place[1] - 1] > 0 && this.board[place[0] - 1][place[1] - 1] < 8) {
                return false;
            }
            //Center
            if (this.board[place[0] - 1][place[1]] > 0 && this.board[place[0] - 1][place[1]] < 8 && !currentBlock.isLeft()) {
                return false;
            }
            //Down
            if (currentBlock.isDown() && this.board[place[0] - 1][place[1] + 1] > 0 && this.board[place[0] - 1][place[1] + 1] < 8 && !currentBlock.isDownLeft()) {
                return false;
            }
        }
        return true;
    }

    //Move left
    public void moveLeft(Blocks currentBlock) {
        //final int[][] finalBoard = this.board;
        int[] place = currentBlock.getPlace();
        int[] oldPlace = currentBlock.getNextBlock().getPlace();
        if (emptyLeft(currentBlock)) {
            //Regular Block
            removeBlock(currentBlock); //Removing the block from the current place
            currentBlock.setPlace(place[0] - 1, place[1]); //Setting the place of the block at the new point
            this.board[place[0] + 1][place[1]] = 0; // Removing the old block from the old position
            insertBlock(currentBlock); // Inserting the block at the new place
            //Land block handle
            removeBlock(currentBlock.getNextBlock());
            this.board[oldPlace[0]][oldPlace[1]] = 0;
            insertBlock(lb.moveLeft(currentBlock,board));
        }
    }
    //If the game has ended
    public boolean endOfGame(Blocks curenntBlock) {
        int[] lastPos = blocksHistory.get(blocksHistory.size() - 1).getPlace();
        if (curenntBlock.isUp() || curenntBlock.isLeftUp() || curenntBlock.isRightUp()) {
            if (lastPos[1] == startJ + 1 && !curenntBlock.isMoving()) {
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        gameActivity.gameOver();
                    }
                });
                return true;
            }
        } else {
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
    public void checkBoard() {
        boolean moveDown = true;
        boolean checkAgain = false;
        int atOnce = 1;
        for (int j = board[0].length - 1; j >= 2; j--) {
            for (int i = 0; i < board.length; i++) {
                if (board[i][j] == 0 || board[i][j] ==8) {
                    moveDown = false;
                }
             /*   if (board[i][j] == 8) {
                    board[i][j] = 0;
                }*/
            }
            if (moveDown) {
                pushDown(j);
                if (atOnce == 1) {
                    score += 40 * ((23 - j) + 1);
                }
                if (atOnce == 2) {
                    score += 100 * ((23 - j) + 1);
                }
                if (atOnce == 3) {
                    score += 300 * ((23 - j) + 1);
                }
                if (atOnce >= 4) {
                    score += 1200 * ((23 - j) + 1);
                }
                //this.score += 100;
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        gameActivity.changeScore(score);
                    }
                });
                checkAgain = true;
                atOnce++;
            }
            moveDown = true;
        }
        if (checkAgain) {
            checkBoard();
        }
    }


    //Moving all the rows down and clearing the full row
    private void pushDown(int j){
        for(int i=0;i< board.length; i++){
            for(int h=j;1<=h;h--){
             /*   if(realBlock(i,h)){

                }else {
                    if (fromPosToBlock(i, h)) {
                        this.board[h][h - 1] = 0;
                    }
                }*/
                board[i][h] = board[i][h-1];
            }
        }
    }
    public void setEmptySpaceBlockPos(final Blocks emptySpot){
        lb.setEmptySpaceBlockPos(emptySpot,board);
    }
    public void landBlockBugFix(){
      /*  int[] lastPost = poistionHistory.get(poistionHistory.size()-1);
        if(this.board[lastPost[0]][lastPost[1]] == 8) {
            if (fromPosToBlock(lastPost[0], lastPost[1]) != null) {
                insertBlock(fromPosToBlock(lastPost[0], lastPost[1]));
            }
        }*/
      for(int i=0; i< this.board.length;i++){
          for(int j=0; j< this.board[i].length; j++){
              if(this.board[i][j] ==8){
                  this.board[i][j] =0;
              }
          }
      }
    }
    //Return the board so you can display it
    public int[][] getDisplay(){
        return this.board;
    }
}

/*TODO
NEED TO DO
--------------------------
Game -- need to design this bullshit + recreate the alert dialog
How to play - everything

onBackPressed for EVERYTHING

Fix all the design I did

Add everything I need to get 100
* */

