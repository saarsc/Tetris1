package com.tetris.saar.tetris;

/**
 * The type Line and up left.
 */
//For the J shape
public class LineAndUpLeft extends Blocks{
    /**
     * Instantiates a new Line and up left.
     *
     * @param i the
     * @param j the j
     */
    public LineAndUpLeft(int i, int j){
        super(true, false,true, true,false,false, false, false,3,i,j);
    }

    /**
     * Instantiates a new Line and up left.
     *
     * @param line the line
     * @param i    the
     * @param j    the j
     */
    public LineAndUpLeft(LineAndUpLeft line , int i, int j){
        super(line,i,j);
    }
    //Rotation
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
                    setLeftUp(true);
                    setLeft(true);
                    setRight(true);
                    setUp(false);
                    setRightUp(false);
                    setDown(false);
                    setDownRight(false);
                    setDownLeft(false);
                    break;
                case 1:
                    setUp(true);
                    setRightUp(true);
                    setDown(true);
                    setLeftUp(false);
                    setLeft(false);
                    setRight(false);
                    setDownLeft(false);
                    setDownRight(false);
                    break;
                case 2:
                    setLeft(true);
                    setRight(true);
                    setDownRight(true);
                    setUp(false);
                    setRightUp(false);
                    setLeftUp(false);
                    setDown(false);
                    setDownLeft(false);
                    break;
                case 3:
                    setDownLeft(true);
                    setDown(true);
                    setUp(true);
                    setLeft(false);
                    setRight(false);
                    setRightUp(false);
                    setLeftUp(false);
                    setDownRight(false);
                    break;
            }
        }
    }
}
