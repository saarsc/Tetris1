package com.tetris.saar.tetris;

/**
 * The type S shaped.
 */
//S shaped block
public class SShaped extends Blocks {
    /**
     * Instantiates a new S shaped.
     *
     * @param i the
     * @param j the j
     */
    public SShaped(int i, int j){
        super(true,true,false,false,true,false,false,false,7,i,j);
    }

    /**
     * Instantiates a new S shaped.
     *
     * @param line the line
     * @param i    the
     * @param j    the j
     */
    public SShaped(SShaped line , int i, int j){
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
                    setLeft(true);
                    setRight(false);
                    setRightUp(true);
                    setUp(true);
                    setDownRight(false);
                    break;
                case 1:
                    setLeft(false);
                    setRight(true);
                    setRightUp(false);
                    setUp(true);
                    setDownRight(true);
                    break;
                case 2:
                    setLeft(true);
                    setRight(false);
                    setRightUp(true);
                    setUp(true);
                    setDownRight(false);
                    break;
                case 3:
                    setLeft(false);
                    setRight(true);
                    setRightUp(false);
                    setUp(true);
                    setDownRight(true);
                    break;
            }
        }
    }
}


