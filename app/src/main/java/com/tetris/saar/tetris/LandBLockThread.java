package com.tetris.saar.tetris;

//Thread that control the LandBlock
public class LandBLockThread extends Thread {
  private GameManger gm;
    public LandBLockThread( GameManger gm) {
        this.gm = gm;
    }

    public void run() {

    }
    //Moving right
    public Blocks moveRight(Blocks block,int[][] board){
        Blocks temp = block.getNextBlock();
        temp.setPlace(block.getPlace()[0],block.getPlace()[1]);
        setEmptySpaceBlockPos(temp,board);
        return temp;
    }
    //Moving left
    public Blocks moveLeft(Blocks block,int[][] board){
        Blocks temp = block.getNextBlock();
        temp.setPlace(block.getPlace()[0],block.getPlace()[1]);
        setEmptySpaceBlockPos(temp,board);
        return temp;
    }
    //Changing the rotation
    public Blocks change(Blocks block,int[][] board){
        Blocks temp = block.getNextBlock();
        temp.setPlace(block.getPlace()[0],block.getPlace()[1]);
        temp.changeRot();
        setEmptySpaceBlockPos(temp,board);
        return temp;
    }
    //Finding the block empty position
    public Blocks setEmptySpaceBlockPos(Blocks block,int[][] board) {
       while(isEmptyDown(block,board))
    {
        block.setPlace(block.getPlace()[0],block.getPlace()[1]+1);
    }
    return  block;
}
    //If there is place underneath
    public boolean isEmptyDown(Blocks currentBlock,int[][] board) {
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
                    if (board[place[0]][place[1] + 2] > 0 && board[place[0]][place[1] + 2] < 8) {
                        empty = false;
                    }

                }
                if (currentBlock.isDownLeft()) {
                    if (board[place[0] - 1][place[1] + 2] > 0 && board[place[0] - 1][place[1] + 2] < 8) {
                        empty = false;
                    }
                }
                if (currentBlock.isDownRight()) {
                    if (board[place[0] + 1][place[1] + 2] > 0 && board[place[0] + 1][place[1] + 2] < 8) {
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
            if (board[place[0]][place[1] + 1] > 0 && board[place[0]][place[1] + 1] < 8 && !currentBlock.isDown()) {
                empty = false;
            }
            //Checking under left
            if (currentBlock.isLeft() && !currentBlock.isDownLeft()) {
                if (board[place[0] - 1][place[1] + 1] > 0 &&board[place[0] - 1][place[1] + 1] < 8) {
                    empty = false;
                }
            } else if (currentBlock.isLeftUp()) {
                if (board[place[0] - 1][place[1]] > 0 && board[place[0] - 1][place[1]] < 8) {
                    empty = false;
                }
            }
            //Checking under right
            if (currentBlock.isRight() && !currentBlock.isDownRight()) {
                if (board[place[0] + 1][place[1] + 1] > 0 && board[place[0] + 1][place[1] + 1] < 8) {
                    empty = false;
                }

            } else
                //Checking under the top right
                if (currentBlock.isRightUp() && board[place[0] + 1][place[1]] > 0 &&board[place[0] + 1][place[1]] < 8) {
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

}
