package com.tetris.saar.tetris;

/**
 * Created by user on 27/08/2017.
 */

public class LineAndUpLeft extends Blocks{
    public LineAndUpLeft(int i, int j){
        super(true, false,true, true,false,false, false, false,3,i,j);
    }
    //Get + Set
    public int getRotation() {
        return rotation;
    }
    @Override
    public void changeRot() {
        if(getRotation() ==3 ){
            this.rotation =0;
        }else{
            this.rotation = getRotation() +1;
        }
        switch(rotation){
            case 0: setLeftUp(true); setLeft(true); setRight(true); setUp(false); setRightUp(false); setDown(false); setDownRight(false); setDownLeft(false);
                break;
            case 1: setUp(true);setRightUp(true); setDown(true); setLeftUp(false); setLeft(false); setRight(false); setDownLeft(false); setDownRight(false);
                break;
            case 2: setLeft(true); setRight(true); setDownRight(true); setUp(false); setRightUp(false); setLeftUp(false); setDown(false); setDownLeft(false);
                break;
            case 3: setDownLeft(true); setDown(true); setUp(true);  setLeft(false);setRight(false);setRightUp(false);setLeftUp(false); setDownRight(false);
                break;
        }
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
