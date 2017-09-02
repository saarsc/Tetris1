package com.tetris.saar.tetris;

/**
 * Created by user on 14/08/2017.
 */

public class Squre extends Blocks{

    public Squre(int i, int j){
       super(false, true,true, false,true,false, false, false,1,i,j);
    }

    //Get + Set
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getRotation() {
        return rotation;
    }

    public void changeRot(){
    }
    public int[] getPlace() {
       return place;
   }

    public void setPlace(int i, int j) {
        this.place[0] =i;
        this.place[1] =j;
    }
    public boolean isMoving() {
        return isMoving;
    }


    public void setMoving(boolean moving) {
        isMoving = moving;
    }
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
