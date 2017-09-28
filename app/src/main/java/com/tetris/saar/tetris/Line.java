package com.tetris.saar.tetris;

/**
 * Created by user on 29/08/2017.
 */

public class Line extends Blocks{
    public Line(int i, int j){
        super(true,false,true,false,false,false,false,false,4,i,j);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getRotation() {
        return rotation;
    }

    @Override
    public void changeRot(){
        if(getRotation() ==3 ){
            this.rotation =0;
        }else{
            this.rotation = getRotation() +1;
        }
        //Setting all the new block shaped
        switch (rotation){
            case 0: setLeft(true); setUp(false); setRight(true); setDown(false);
                break;
            case 1: setLeft(false); setUp(true); setRight(false); setDown(true);
                break;
            case 2: setLeft(true); setUp(false); setRight(true); setDown(false);
                break;
            case 3: setLeft(false); setUp(true); setRight(false); setDown(true);
                break;
        }

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


