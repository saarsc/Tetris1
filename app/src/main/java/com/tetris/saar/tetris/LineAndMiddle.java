package com.tetris.saar.tetris;

/**
 * Created by user on 29/08/2017.
 */
//Class for t shaped block
public class LineAndMiddle extends Blocks {
    public LineAndMiddle(int i , int j){
        super(true,true,true,false,false,false,false,false,6,i,j);
    }
    public LineAndMiddle(LineAndMiddle line , int i, int j){
        super(line,i,j);
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
                setUp(true);
                setLeft(true);
                setRight(true);
                setDown(false);
                break;
            case 1:
                setUp(true);
                setRight(true);
                setDown(true);
                setLeft(false);
                break;
            case 2:
                setLeft(true);
                setRight(true);
                setDown(true);
                setUp(false);
                break;
            case 3:
                setUp(true);
                setLeft(true);
                setDown(true);
                setRight(false);
                break;
        }
    }
    }

}
