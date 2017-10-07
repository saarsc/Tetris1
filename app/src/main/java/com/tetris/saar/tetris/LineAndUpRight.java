package com.tetris.saar.tetris;

/**
 * Created by user on 15/08/2017.
 */

public class LineAndUpRight extends Blocks{
    public LineAndUpRight(int i, int j){
        super(true,false,true,false,true,false,false,false,2,i,j);
    }
    public LineAndUpRight(LineAndUpRight line , int i, int j){
        super(line,i,j);
    }
    public int getRotation() {
        return rotation;
    }
    @Override
    public void changeRot() {
        if(this.id==8 && this.getPlace()[1] ==23){
            this.setPlace(this.getPlace()[0],this.getPlace()[1]-1);
            changeRot();
        }else {
            if (getRotation() == 3) {
                this.rotation = 0;
            } else {
                this.rotation = getRotation() + 1;
            }
            switch (rotation) {
                case 0:
                    setRightUp(true);
                    setRight(true);
                    setLeft(true);
                    setUp(false);
                    setDownRight(false);
                    setDownLeft(false);
                    setDown(false);
                    setLeftUp(false);
                    break;
                case 1:
                    setUp(true);
                    setDown(true);
                    setDownRight(true);
                    setLeft(false);
                    setRight(false);
                    setRightUp(false);
                    setLeftUp(false);
                    setDownLeft(false);
                    break;
                case 2:
                    setDownLeft(true);
                    setLeft(true);
                    setRight(true);
                    setDown(false);
                    setDownRight(false);
                    setLeftUp(false);
                    setUp(false);
                    setRightUp(false);
                    break;
                case 3:
                    setUp(true);
                    setLeftUp(true);
                    setDown(true);
                    setLeft(false);
                    setDownLeft(false);
                    setDownRight(false);
                    setRight(false);
                    setRightUp(false);
                    break;

            }
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
