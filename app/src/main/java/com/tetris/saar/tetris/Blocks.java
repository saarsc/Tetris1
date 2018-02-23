package com.tetris.saar.tetris;

/**
 * Created by user on 14/08/2017.
 */
//Main class for all the blocks to extend
public class Blocks {

    //Main block class all the blocks extend this class
    //Boolean vars that define the blocks around the main block + built for all directions
    /**
     * The Left. block
     */
    protected  boolean left;
    /**
     * The Up block.
     */
    protected boolean up;
    /**
     * The Right block.
     */
    protected boolean right;
    /**
     * The Left up block.
     */
    protected boolean leftUp;
    /**
     * The Right up block.
     */
    protected boolean rightUp;
    /**
     * The Down block.
     */
    protected boolean down;
    /**
     * The Down left block.
     */
    protected boolean downLeft;
    /**
     * The Down right block.
     */
    protected boolean downRight;
    /**
     * The Id of the block.
     */
    protected int id;
    /**
     * The Place of the block.
     */
    protected int[] place;
    /**
     * Is the block moving
     */
    protected boolean isMoving;
    /**
     * The Rotation of the block.
     */
    protected int rotation;

    /**
     * The landblock.
     */
    protected Blocks nextBlock; //Holds the next block

    /**
     * Instantiates a new Blocks.
     *
     * @param left      the left
     * @param up        the up
     * @param right     the right
     * @param leftUp    the left up
     * @param rightUp   the right up
     * @param down      the down
     * @param downLeft  the down left
     * @param downRight the down right
     * @param id        the id
     * @param i         the
     * @param j         the j
     */
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

    /**
     * Get next block blocks.
     *
     * @return the blocks
     */
    public Blocks getNextBlock(){
        return this.nextBlock;
    }

    /**
     * Set land block.
     *
     * @param nextBlock the next block
     */
    protected void setNextBlock(Blocks nextBlock){
        this.nextBlock= nextBlock;
    }

    /**
     * Instantiates a new Blocks.
     *
     * @param block the block
     * @param i     the
     * @param j     the j
     */
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
    /**
     * Change rotation.
     *
     * @return the rotation
     */
    public int getRotation() {
        return rotation;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get place int [ ].
     *
     * @return the place [ ]
     */
    public int[] getPlace() {
        return place;
    }

    /**
     * Sets place.
     *
     * @param i the
     * @param j the j
     */
    public void setPlace(int i, int j) {
        this.place[0] =i;
        this.place[1] =j;
    }

    /**
     * If the block is moving
     *
     * @return the boolean
     */
    public boolean isMoving() {
        return isMoving;
    }

    /**
     * Sets moving.
     *
     * @param moving the moving
     */
    public void setMoving(boolean moving) {
        isMoving = moving;
    }
    //Getting and setting all the direction of the block
    /**
     * Is left boolean.
     *
     * @return the boolean
     */
    public boolean isLeft() {
        return left;
    }

    /**
     * Sets left.
     *
     * @param left the left
     */
    public void setLeft(boolean left) {
        this.left = left;
    }

    /**
     * Is up boolean.
     *
     * @return the boolean
     */
    public boolean isUp() {
        return up;
    }

    /**
     * Sets up.
     *
     * @param up the up
     */
    public void setUp(boolean up) {
        this.up = up;
    }

    /**
     * Is right boolean.
     *
     * @return the boolean
     */
    public boolean isRight() {
        return right;
    }

    /**
     * Sets right.
     *
     * @param right the right
     */
    public void setRight(boolean right) {
        this.right = right;
    }

    /**
     * Is left up boolean.
     *
     * @return the boolean
     */
    public boolean isLeftUp() {
        return leftUp;
    }

    /**
     * Sets left up.
     *
     * @param leftUp the left up
     */
    public void setLeftUp(boolean leftUp) {
        this.leftUp = leftUp;
    }

    /**
     * Is right up boolean.
     *
     * @return the boolean
     */
    public boolean isRightUp() {
        return rightUp;
    }

    /**
     * Sets right up.
     *
     * @param rightUp the right up
     */
    public void setRightUp(boolean rightUp) {
        this.rightUp = rightUp;
    }

    /**
     * Is down boolean.
     *
     * @return the boolean
     */
    public boolean isDown() {
        return down;
    }

    /**
     * Sets down.
     *
     * @param down the down
     */
    public void setDown(boolean down) {
        this.down = down;
    }

    /**
     * Is down left boolean.
     *
     * @return the boolean
     */
    public boolean isDownLeft() {
        return downLeft;
    }

    /**
     * Sets down left.
     *
     * @param downLeft the down left
     */
    public void setDownLeft(boolean downLeft) {
        this.downLeft = downLeft;
    }

    /**
     * Is down right boolean.
     *
     * @return the boolean
     */
    public boolean isDownRight() {
        return downRight;
    }

    /**
     * Sets down right.
     *
     * @param downRight the down right
     */
    public void setDownRight(boolean downRight) {
        this.downRight = downRight;
    }

    /**
     * Change rot.
     */
    //Changing the rotation
    public void changeRot() {

    }

}
