package com.tetris.saar.tetris;

/**
 * Created by user on 14/08/2017.
 */
//Main class for all the blocks to extend
public class Blocks {
    //Main block class all the blocks extend this class
    //Boolean vars that define the blocks around the main block + built for all directions
    protected  boolean left;
    protected boolean up;
    protected boolean right;
    protected boolean leftUp;
    protected boolean rightUp;
    protected boolean down;
    protected boolean downLeft;
    protected boolean downRight;

    protected int id; //Block id
    protected int[] place; //Block place
    protected boolean isMoving; //Is the block moving
    protected int rotation; //Which rotation is right now

    protected Blocks nextBlock; //Holds the next block
    public Blocks(boolean left, boolean up, boolean right, boolean leftUp, boolean rightUp, boolean down, boolean downLeft, boolean downRight, int id, int i, int j){
        this.left = left;
        this.up = up;
        this.right = right;
        this.leftUp = leftUp;
        this.rightUp = rightUp;
        this.down = down;
        this.downLeft = downLeft;
        this.downRight = downRight;
        this.id = id;
        this.isMoving = true;
        this.place =new int[2];
        this.place[0] = i;
        this.place[1]=j;
        this.rotation=0;

    }
    public Blocks getNextBlock(){
        return this.nextBlock;
    }
    public boolean hasNextBlock(){
        return this.nextBlock != null;
    }
    //Where the block is going to land
    protected void setNextBlock(Blocks nextBlock){
        this.nextBlock= nextBlock;
    }
    //Copying the given block
    public Blocks(Blocks block,int i, int j){
        this.left = block.isLeft();
        this.up=block.isUp();
        this.right = block.isRight();
        this.leftUp = block.isLeftUp();
        this.rightUp = block.isRightUp();
        this.down = block.isDown();
        this.downLeft = block.isDownLeft();
        this.downRight= block.isDownRight();
        this.id = 8;
        this.isMoving = true;
        this.place = new int[2];
        this.place[0] =i;
        this.place[1] = j;
        this.rotation = block.getRotation();
    }
    //Getters and Setters
    //Changes the rotations
    public int getRotation() {
        return rotation;
    }
    //Return the id
    public int getId() {
        return id;
    }
    //Setting the id
    public void setId(int id) {
        this.id = id;
    }
    //Getting the position of the block
    public int[] getPlace() {
        return place;
    }
    //Setting the position of the block
    public void setPlace(int i, int j) {
        this.place[0] =i;
        this.place[1] =j;
    }
    //If the block is moving
    public boolean isMoving() {
        return isMoving;
    }
    //Change if the block is moving or not
    public void setMoving(boolean moving) {
        isMoving = moving;
    }
    //Getting and setting all the direction of the block
    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isLeftUp() {
        return leftUp;
    }

    public void setLeftUp(boolean leftUp) {
        this.leftUp = leftUp;
    }

    public boolean isRightUp() {
        return rightUp;
    }

    public void setRightUp(boolean rightUp) {
        this.rightUp = rightUp;
    }
    //Changing the rotation
    public void changeRot() {

    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isDownLeft() {
        return downLeft;
    }

    public void setDownLeft(boolean downLeft) {
        this.downLeft = downLeft;
    }

    public boolean isDownRight() {
        return downRight;
    }

    public void setDownRight(boolean downRight) {
        this.downRight = downRight;
    }


}
