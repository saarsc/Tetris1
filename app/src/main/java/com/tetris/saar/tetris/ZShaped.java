package com.tetris.saar.tetris;

/**
 * The type Z shaped.
 */
// Z Shape
public class ZShaped extends Blocks{
    /**
     * Instantiates a new Z shaped.
     *
     * @param i the
     * @param j the j
     */
    public ZShaped(int i, int j){
        super(false,true,true,true,false,false,false,false,5,i,j);
    }

    /**
     * Instantiates a new Z shaped.
     *
     * @param line the line
     * @param i    the
     * @param j    the j
     */
    public ZShaped(ZShaped line , int i, int j){
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
                    setUp(true);
                    setRight(true);
                    setLeftUp(true);
                    setLeft(false);
                    setDownLeft(false);
                    break;
                case 1:
                    setUp(true);
                    setLeft(true);
                    setDownLeft(true);
                    setRight(false);
                    setLeftUp(false);
                    break;
                case 2:
                    setUp(true);
                    setRight(true);
                    setLeftUp(true);
                    setLeft(false);
                    setDownLeft(false);
                    break;
                case 3:
                    setUp(true);
                    setLeft(true);
                    setDownLeft(true);
                    setRight(false);
                    setLeftUp(false);
                    break;
            }
        }
    }
}
