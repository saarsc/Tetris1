package com.tetris.saar.tetris;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static com.tetris.saar.tetris.R.mipmap.blue;
import static com.tetris.saar.tetris.R.mipmap.darkblue;
import static com.tetris.saar.tetris.R.mipmap.green;
import static com.tetris.saar.tetris.R.mipmap.grey;
import static com.tetris.saar.tetris.R.mipmap.orange;
import static com.tetris.saar.tetris.R.mipmap.purple;
import static com.tetris.saar.tetris.R.mipmap.red;
import static com.tetris.saar.tetris.R.mipmap.yellow;

public class HowToPlay extends AppCompatActivity implements View.OnClickListener,View.OnTouchListener{
    public ImageView[][] blocks = new ImageView[10][24]; //Displayed board
    public int[][] board = new int[10][24];
    TextView tvDesc;
    TextView tvScore;
    ArrayList<String> text = new ArrayList<>();

    Button btnNext;
    Button btnPrev;
    Button btnBack;
    //Next Block place
    ImageView[][] nextBlockView = new ImageView[4][4];
    //Swipe checker
    float x1,x2,downY,upY;
    static final int MIN_DISTANCE = 100;

    int turn =0;

    android.os.Handler uiHandler;
    //Each level enabler
    boolean moveRight = false;
    boolean moveLeft = false;
    boolean tap =false;
    boolean drop = true;
    Blocks block = new LineAndMiddle(5,6);
    //Main menu handle
    Menu mainMenu = null;
    //Main second thread
    Thread t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_play);
        //Syncing the java and the GUI
        LinearLayout layout = (LinearLayout)findViewById(R.id.mainLayout);
        LinearLayout nextBlockLayout = (LinearLayout) findViewById(R.id.nextBlockLayout);

        tvDesc = (TextView) findViewById(R.id.tvDesc);
        tvScore = (TextView) findViewById(R.id.tvScore);
        btnNext =(Button) findViewById(R.id.btnNext);
        btnPrev =(Button) findViewById(R.id.btnPrev);
        btnBack = (Button) findViewById(R.id.btnBack);

        btnNext.setOnClickListener(this);
        btnPrev.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        uiHandler = new Handler();
        init();
        //Creating all the ImageView game board
        for(int i=0; i< blocks.length; i++) {
            LinearLayout row = new LinearLayout(this);
            if(i==0){
                LinearLayout.LayoutParams leftMarhin = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                leftMarhin.setMargins(180,0,0,0);
                row.setLayoutParams(leftMarhin);
            }
            row.setOrientation(LinearLayout.VERTICAL);
            layout.addView(row);
            for (int j = 2; j < blocks[i].length; j++) {
                ImageView image = new ImageView(this);
                //image.setBackgroundColor(Color.rgb(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255)));
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(65, 65);

                lp.setMargins(0, 0, 0, 0);
                image.setLayoutParams(lp);
                image.setMaxHeight(0);
                image.setMaxWidth(0);
                image.setPadding(10, 10, 10, 10);
                blocks[i][j] = image;
                row.addView(image);
            }

        }
        //Creating all the ImageView for the next block view
        for(int i=0; i< nextBlockView.length;i++){
            LinearLayout row = new LinearLayout(this);
            if(i==0){
                LinearLayout.LayoutParams leftMarhin = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                leftMarhin.setMargins(870,0,0,0);
                row.setLayoutParams(leftMarhin);
            }
            row.setOrientation(LinearLayout.VERTICAL);
            nextBlockLayout.addView(row);
            for (int j = 0; j < nextBlockView[i].length; j++) {
                ImageView image = new ImageView(this);
                image.setBackgroundColor(Color.BLACK);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(53, 53);
                if(j==0){
                    lp.setMargins(0,390,0,0);
                }
                // lp.setMargins(0, 0, 0, 0);
                image.setLayoutParams(lp);
                image.setMaxHeight(0);
                image.setMaxWidth(0);
                image.setPadding(10, 10, 10, 10);
                nextBlockView[i][j] = image;
                row.addView(image);
            }
        }
        toDisplay();
        update();
    }
    //Main handle
    public void update(){
        if(turn ==0){
            clear();
            btnPrev.setVisibility(View.INVISIBLE);
            tvDesc.setText(text.get(turn));
            moveRight = false;
            drop =true;
            drop();
        }
        if(turn == 1){
            drop = false;
         /*   try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            //clear();
            block = new LineAndMiddle(5,6);
            insertBlock(block);
            btnPrev.setVisibility(View.VISIBLE);
            tvDesc.setText(text.get(turn));
            moveRight = true;
            moveLeft = false;
        }
        if(turn == 2){
            moveRight = false;
            clear();
            btnPrev.setVisibility(View.VISIBLE);
            tvDesc.setText(text.get(turn));
            moveLeft = true;
        }
        if(turn ==3){
            moveLeft = false;
            clear();
            tvDesc.setText(text.get(turn));
            tap = true;
        }
        if(turn ==4){
            tap= false;
            clear();
            tvDesc.setText(text.get(turn));
            putNextBlock();
        }
        if(turn == 5){
            clear();
            tvDesc.setText(text.get(turn));
            completeLine();
        }
        if(turn ==6){
            clear();
            tvDesc.setText(text.get(turn));
        }
        toDisplay();
    }
    //Showing the completing line part
    public void completeLine(){
        Line l1 = new Line(8,23);
        Line l2 = new Line(5,23);
        Line l3 = new Line(1,23);
        final Line l4 = new Line(3,1);
        l4.changeRot();
        insertBlock(l1);
        insertBlock(l2);
        insertBlock(l3);
        toDisplay();
         t = new Thread(new Runnable() {
            @Override
            public void run() {
                while(l4.getPlace()[1] != 22) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    removeBlock(l4);
                    l4.setPlace(l4.getPlace()[0], l4.getPlace()[1] + 1);
                    insertBlock(l4);
                    uiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            toDisplay();
                        }
                    });
                }
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        pushDown();
                        tvScore.setText("Score:\n40");
                    }
                });
            }
        });
        t.start();
    }
    //Push down handle
    private void pushDown(){
        for(int i=0;i< board.length; i++){
            for(int h=board[i].length-1;1<=h;h--){
                board[i][h] = board[i][h-1];
            }
        }
        toDisplay();
    }
    //Displaying the next block
    public void putNextBlock(){
       /* nextBlockView[2][2].setBackgroundColor(Color.rgb(135,206,250));
        nextBlockView[3][2].setBackgroundColor(Color.rgb(135,206,250));
        nextBlockView[1][2].setBackgroundColor(Color.rgb(135,206,250));*/
       nextBlockView[2][2].setBackgroundResource(blue);
        nextBlockView[3][2].setBackgroundResource(blue);
        nextBlockView[1][2].setBackgroundResource(blue);
    }
    //Drop the block
    public void drop(){
      t  =new Thread(new Runnable() {
            @Override
            public void run() {
                final Blocks block = new LineAndMiddle(5,1);
                insertBlock(block);
                while(block.getPlace()[1] != 23 &&drop){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    removeBlock(block);
                    block.setPlace(block.getPlace()[0],block.getPlace()[1]+1);
                    insertBlock(block);
                   uiHandler.post(new Runnable() {
                       @Override
                       public void run() {
                           toDisplay();
                       }
                   });
                }
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        clear();
                    }
                });
            }
        });
        t.start();

    }
    //Removing a given block from the board
    private void removeBlock(Blocks block) {
        int[] place = block.getPlace();
        if (place[0] != 0) {
            if (block.isLeft()) {
                board[place[0] - 1][place[1]] = 0;
            }
            if (block.isLeftUp()) {
                board[place[0] - 1][place[1] - 1] = 0;
            }
            if (block.isDownLeft()) {
               board[place[0] - 1][place[1] + 1] = 0;
            }
        }
        if (block.isUp()) {
            board[place[0]][place[1] - 1] = 0;
        }
        if (place[0] != 9) {
            if (block.isRight()) {
                board[place[0] + 1][place[1]] = 0;
            }
            if (block.isRightUp()) {
                board[place[0] + 1][place[1] - 1] = 0;
            }
            if (block.isDownRight()) {
                board[place[0] + 1][place[1] + 1] = 0;
            }
        }

        if (block.isDown()) {
            board[place[0]][place[1] + 1] = 0;
        }
    }
    //Insert the block in the right place and place all the blocks around the center block
    public void insertBlock(Blocks newBlock) {
        int id = newBlock.getId();
        int[] currentPlace = newBlock.getPlace();
        board[currentPlace[0]][currentPlace[1]] = id;
        if (newBlock.isUp()) {
            board[currentPlace[0]][currentPlace[1] - 1] = id;
        }
        if (newBlock.isLeft()) {
            board[currentPlace[0] - 1][currentPlace[1]] = id;
        }
        if (newBlock.isRight()) {
            board[currentPlace[0] + 1][currentPlace[1]] = id;
        }
        if (newBlock.isLeftUp()) {
            board[currentPlace[0] - 1][currentPlace[1] - 1] = id;
        }
        if (newBlock.isRightUp()) {
            board[currentPlace[0] + 1][currentPlace[1] - 1] = id;
        }
        if (newBlock.isDown()) {
            board[currentPlace[0]][currentPlace[1] + 1] = id;
        }
        if (newBlock.isDownLeft()) {
            board[currentPlace[0] - 1][currentPlace[1] + 1] = id;
        }
        if (newBlock.isDownRight()) {
            board[currentPlace[0] + 1][currentPlace[1] + 1] = id;
        }
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == btnNext.getId()){
            turn++;
            update();
        }
        if(v.getId() == btnPrev.getId()){
            turn--;
            update();
        }
        if(v.getId() == btnBack.getId()){
            Intent intent = new Intent(HowToPlay.this,MainMenu.class);
            startActivity(intent);
        }
    }
    //Moving right
    public void moveRight(){
         t = new Thread(new Runnable() {
            @Override
            public void run() {
                if(block.getPlace()[0]!= 8){
                    removeBlock(block);
                    block.setPlace(block.getPlace()[0]+1,block.getPlace()[1]);
                    insertBlock(block);
                    uiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            toDisplay();
                        }
                    });
                }
            }
        });
        t.start();
    }
    //Moving left
    public void moveLeft(){
         t = new Thread(new Runnable() {
            @Override
            public void run() {
                if(block.getPlace()[0]!=1){
                    removeBlock(block);
                    block.setPlace(block.getPlace()[0]-1,block.getPlace()[1]);
                    insertBlock(block);
                    uiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            toDisplay();
                        }
                    });
                }
            }
        });
        t.start();
    }
    //Swipe control
    @Override
    public boolean onTouchEvent(MotionEvent event){
        {
            switch(event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    x1 = event.getX();
                    downY = event.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    x2 = event.getX();
                    upY = event.getY();
                    float deltaX = x2 - x1;
                    float deltaY = upY- downY;
                    if (Math.abs(deltaX) > MIN_DISTANCE)
                    {
                        //Move right
                        if (x2 > x1)
                        {
                            if(moveRight) {
                                moveRight();
                            }
                        }
                        // Right to left swipe action
                        else
                        {
                            if(moveLeft){
                                moveLeft();
                            }
                        }
                    }//VERTICAL SCROLL
                    else
                    if(Math.abs(deltaY) > MIN_DISTANCE){
                        // top or down
                        if(deltaY > 0)
                        {

                            return true;
                        }
                    }
                    else
                    {
                        if(tap){
                            rotate();
                        }
                    }
                    break;
            }
            return super.onTouchEvent(event);
        }
    }
    //Rotating the block
    public void rotate(){
        t= new Thread(new Runnable() {
            @Override
            public void run() {
                removeBlock(block);
                block.setPlace(5,12);
                block.changeRot();
                insertBlock(block);
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        toDisplay();
                    }
                });
            }
        });
       t.start();
    }
    //Creating the array
    public void init(){
        text.add("The block will drop by it self");
        text.add("Swipe right to move right");
        text.add("Swipe left to move left");
        text.add("Tap to rotate the block");
        text.add("Under \"Next Block\" you will \nsee the next block");
        text.add("Complete lines to gain score");
        text.add("You can pause by pressing \nthe pause button");
    }
    //Display the board ---> convert from int to colored Image View
    public void toDisplay(){
        for(int i=blocks.length-1; i>=0; i--){
            for(int j=blocks[i].length-1; j>=2; j--){
                //Empty block
                if(board[i][j]==0){
                    blocks[i][j].setBackgroundColor(Color.argb(1000,0,0,0));
                    //blocks[i][j].setBackgroundResource(black);
                }
                //Square
                if(board[i][j] == 1){
                    //blocks[i][j].setBackgroundColor(Color.YELLOW);
                    blocks[i][j].setBackgroundResource(yellow);
                }
                //Line and up right
                if(board[i][j] == 2){
                    //blocks[i][j].setBackgroundColor(Color.rgb(255,140,0));
                    blocks[i][j].setBackgroundResource(orange);
                }
                //Line and up left
                if(board[i][j]==3){
                    //blocks[i][j].setBackgroundColor(Color.rgb(0,0,205));
                    blocks[i][j].setBackgroundResource(darkblue);
                }
                //Line
                if(board[i][j]==4){
                    //blocks[i][j].setBackgroundColor(Color.rgb(135,206,250));
                    blocks[i][j].setBackgroundResource(blue);
                }
                //Z shaperd
                if(board[i][j]==5){
                    //blocks[i][j].setBackgroundColor(Color.RED);
                    blocks[i][j].setBackgroundResource(red);
                }
                //T shaped
                if(board[i][j]==6){
                    //blocks[i][j].setBackgroundColor(Color.rgb(138,43,226));
                    blocks[i][j].setBackgroundResource(purple);
                }
                //S shaped
                if(board[i][j]==7){
                    //blocks[i][j].setBackgroundColor(Color.GREEN);
                    blocks[i][j].setBackgroundResource(green);
                }
                if(board[i][j] ==8){
                    //blocks[i][j].setBackgroundColor(Color.GRAY);
                    blocks[i][j].setBackgroundResource(grey);
                }
            }

        }

    }
    //Clearing the board
    public void clear(){
        for(int i=0; i< board.length;i++){
            for(int j=0; j< board[i].length;j++){
                board[i][j] =0;
            }
        }
        toDisplay();
    }
    //Action bar handle
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bulletmenu, menu);
        mainMenu=menu;
        return true;
    }
    //Menu press should open 3 dot menu
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode== KeyEvent.KEYCODE_MENU) {
            mainMenu.performIdentifierAction(R.id.call, 0);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    //Click listener
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()){
            case R.id.call:
                Intent call= new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + ""));
                startActivity(call);
                break;
        }
        return true;
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
