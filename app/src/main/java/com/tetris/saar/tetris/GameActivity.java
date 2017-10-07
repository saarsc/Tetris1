package com.tetris.saar.tetris;import android.content.Context;import android.content.DialogInterface;import android.content.Intent;import android.database.sqlite.SQLiteDatabase;import android.graphics.Color;import android.media.Image;import android.support.annotation.NonNull;import android.support.v7.app.ActionBar;import android.support.v7.app.AlertDialog;import android.support.v7.app.AppCompatActivity;import android.os.Bundle;import android.view.MotionEvent;import android.view.View;import android.view.ViewGroup;import android.widget.Button;import android.widget.EditText;import android.widget.ImageView;import android.widget.LinearLayout;import android.widget.TextView;import android.widget.Toast;import java.io.File;import java.lang.reflect.Array;import java.util.Random;import static com.tetris.saar.tetris.R.mipmap.lego_blocks_detail;public class GameActivity extends AppCompatActivity implements View.OnClickListener,View.OnTouchListener {    public ImageView[][] blocks = new ImageView[10][24]; //Displayed board    //Game Manager    GameManger gameManger = new GameManger(this);    //Used to run the game on a different thread    Runnable game= new GameThread(gameManger);    Thread thread =new Thread(game);    GameThread gameThread = new GameThread(gameManger);    //Buttons    Button btnRot;    Button btnRight;    Button btnLeft;    //Score TextView    TextView tvScore;    //Next Block place    ImageView[][] nextBlockView = new ImageView[4][4];    //Swipe checker    private float x1,x2,downY,upY;    static final int MIN_DISTANCE = 100;    //This screen    Context context;    //For the database    SQLiteDatabase mainDB = null;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_game);        //Creating the Database        createDB();        //Random rnd = new Random();        //Syncing the java and the GUI        LinearLayout layout = (LinearLayout)findViewById(R.id.mainLayout);        LinearLayout nextBlockLayout = (LinearLayout) findViewById(R.id.nextBlockLayout);   /*     btnRot = (Button)findViewById(R.id.btnRot);        btnRight = (Button)findViewById(R.id.btnRight);        btnLeft = (Button)findViewById(R.id.btnLeft);        btnRot.setOnClickListener(this);        btnRight.setOnClickListener(this);        btnLeft.setOnClickListener(this);*/        tvScore = (TextView) findViewById(R.id.tvScore);        context = this;        //Creating all the ImageView game board        for(int i=0; i< blocks.length; i++) {            LinearLayout row = new LinearLayout(this);            if(i==0){                LinearLayout.LayoutParams leftMarhin = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);                leftMarhin.setMargins(230,0,0,0);                row.setLayoutParams(leftMarhin);            }            row.setOrientation(LinearLayout.VERTICAL);            layout.addView(row);            for (int j = 2; j < blocks[i].length; j++) {                    ImageView image = new ImageView(this);                    //image.setBackgroundColor(Color.rgb(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255)));                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(65, 65);                    lp.setMargins(0, 0, 0, 0);                    image.setLayoutParams(lp);                    image.setMaxHeight(0);                    image.setMaxWidth(0);                    image.setPadding(10, 10, 10, 10);                    blocks[i][j] = image;                    row.addView(image);            }        }        //Creating all the ImageView for the next block view        for(int i=0; i< nextBlockView.length;i++){            LinearLayout row = new LinearLayout(this);            if(i==0){                LinearLayout.LayoutParams leftMarhin = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);                leftMarhin.setMargins(800,0,0,0);                row.setLayoutParams(leftMarhin);            }            row.setOrientation(LinearLayout.VERTICAL);            nextBlockLayout.addView(row);            for (int j = 0; j < nextBlockView[i].length; j++) {                ImageView image = new ImageView(this);                image.setBackgroundColor(Color.BLACK);                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(67, 67);                if(j==0){                    lp.setMargins(0,70,0,0);                }               // lp.setMargins(0, 0, 0, 0);                image.setLayoutParams(lp);                image.setMaxHeight(0);                image.setMaxWidth(0);                image.setPadding(10, 10, 10, 10);                nextBlockView[i][j] = image;                row.addView(image);            }        }        toDisplay();        //Starting the game        thread.start();    }    public void gameOver(){        String tempScore = tvScore.getText().toString();      final String[] score = tempScore.split("\n");        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);        builder1.setMessage("You lost. \nYour score is:" +score[1]);        builder1.setTitle("GAME OVER");        builder1.setCancelable(false);        builder1.setPositiveButton(                "Save Score",                new DialogInterface.OnClickListener() {                    public void onClick(DialogInterface dialog, int id) {                        final EditText input = new EditText(context);                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(                                LinearLayout.LayoutParams.MATCH_PARENT,                                LinearLayout.LayoutParams.MATCH_PARENT);                        input.setLayoutParams(lp);                        AlertDialog.Builder saveAlert = new AlertDialog.Builder(context);                        saveAlert.setTitle("Save Your Score:");                        saveAlert.setMessage("Please Enter Your Name:");                        saveAlert.setCancelable(false);                        saveAlert.setPositiveButton("Enter",new DialogInterface.OnClickListener() {                            public void onClick(DialogInterface dialog, int id) {                                Intent intent = new Intent(context,Scoreboard.class);                               /* intent.putExtra("name",input.getText().toString());                                intent.putExtra("score",score[1]);*/                                mainDB.execSQL("INSERT INTO scoreboard1 (name, score) VALUES('" + input.getText() + "','" +Integer.parseInt(score[1]) + "');");                                startActivity(intent);                            }});                        saveAlert.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {                            public void onClick(DialogInterface dialog, int id) {                                gameOver();                            }});                        saveAlert.setView(input);                        AlertDialog alert = saveAlert.create();                        alert.show();                        alert.getWindow().setBackgroundDrawableResource(lego_blocks_detail);                    }                });        builder1.setNeutralButton("Scoreboard",new DialogInterface.OnClickListener() {                    public void onClick(DialogInterface dialog, int id) {                        Intent intent = new Intent(context,Scoreboard.class);                        startActivity(intent);                    }});        builder1.setNegativeButton(                "Menu",                new DialogInterface.OnClickListener() {                    public void onClick(DialogInterface dialog, int id) {                        Intent intent = new Intent(context,MainMenu.class);                        startActivity(intent);                    }                });        AlertDialog alert11 = builder1.create();        alert11.show();        alert11.getWindow().setBackgroundDrawableResource(lego_blocks_detail);    }    //Display the board ---> convert from int to colored Image View    public void toDisplay(){    int[][] board=  gameManger.getDisplay();        for(int i=blocks.length-1; i>=0; i--){            for(int j=blocks[i].length-1; j>=2; j--){                //Empty block                if(board[i][j]==0){                 blocks[i][j].setBackgroundColor(Color.argb(1000,0,0,0));                }                //Square                if(board[i][j] == 1){                    blocks[i][j].setBackgroundColor(Color.YELLOW);                }                //Line and up right                if(board[i][j] == 2){                    blocks[i][j].setBackgroundColor(Color.rgb(255,140,0));                }                //Line and up left                if(board[i][j]==3){                    blocks[i][j].setBackgroundColor(Color.rgb(0,0,205));                }                //Line                if(board[i][j]==4){                    blocks[i][j].setBackgroundColor(Color.rgb(135,206,250));                }                //Z shaperd                if(board[i][j]==5){                    blocks[i][j].setBackgroundColor(Color.RED);                }                //T shaped                if(board[i][j]==6){                    blocks[i][j].setBackgroundColor(Color.rgb(138,43,226));                }                //S shaped                if(board[i][j]==7){                    blocks[i][j].setBackgroundColor(Color.GREEN);                }            }        }    }    @Override    public boolean onTouchEvent(MotionEvent event){        {            switch(event.getAction())            {                case MotionEvent.ACTION_DOWN:                    x1 = event.getX();                    downY = event.getY();                    break;                case MotionEvent.ACTION_UP:                    x2 = event.getX();                    upY = event.getY();                    float deltaX = x2 - x1;                    float deltaY = upY- downY;                    if (Math.abs(deltaX) > MIN_DISTANCE)                    {                        //Move right                        if (x2 > x1)                        {                            gameThread.moveRight();                        }                        // Right to left swipe action                        else                        {                            gameThread.moveLeft();                        }                    }//VERTICAL SCROLL                    else                        if(Math.abs(deltaY) > MIN_DISTANCE){                            // top or down                            if(deltaY > 0)                            {                                gameThread.changeSpeed();                                return true;                            }                        }                    else                    {                            gameThread.needToChange();                    }                    break;            }            return super.onTouchEvent(event);        }    }    //Checking for button click    public void onClick(View view){ /*       if(view.getId() == btnRot.getId()){            gameThread.needToChange();        }        if(view.getId() == btnRight.getId()){            gameThread.moveRight();        }        if(view.getId() ==btnLeft.getId()){            gameThread.moveLeft();        }*/    }    public void changeScore(int text){        tvScore.setText("Score: \n" + text);    }    @Override    public boolean onTouch(View v, MotionEvent event) {        {            /*switch(event.getAction())            {                case MotionEvent.ACTION_DOWN:                    x1 = event.getX();                    break;                case MotionEvent.ACTION_UP:                    x2 = event.getX();                    float deltaX = x2 - x1;                    if (Math.abs(deltaX) > MIN_DISTANCE)                    {                        if (x2 > x1)                        {                            gameThread.moveRight();                        }                        // Right to left swipe action                        else                        {                            gameThread.moveLeft();                        }                    }                    else                    {                        gameThread.needToChange();                    }                    break;            }            return super.onTouchEvent(event);*/        }        return true;    }    //Gets the next block and coverts it's id to color    public void displayNextBlock(Blocks nextBlock){        int id = nextBlock.getId();        if(id==1){            colorNextBlock(Color.YELLOW,nextBlock);        }        if(id == 2){            colorNextBlock(Color.rgb(255,140,0),nextBlock);        }        //Line and up left        if(id==3){            colorNextBlock(Color.rgb(0,0,205),nextBlock);        }        //Line        if(id==4){           colorNextBlock(Color.rgb(135,206,250),nextBlock);        }        //Z shaperd        if(id==5){            colorNextBlock(Color.RED,nextBlock);        }        //T shaped        if(id==6){            colorNextBlock(Color.rgb(138,43,226),nextBlock);        }        //S shaped        if(id==7){            colorNextBlock(Color.GREEN,nextBlock);        }    }    //Color the next block view    private void colorNextBlock(int color,Blocks nextBlock){        for(int i=0; i<nextBlockView.length;i++){            for(int j=0; j< nextBlockView[i].length; j++){                nextBlockView[i][j].setBackgroundColor(Color.BLACK);            }        }        nextBlockView[1][1].setBackgroundColor(color);        if(nextBlock.isLeftUp()){            nextBlockView[0][0].setBackgroundColor(color);        }        if(nextBlock.isUp()){            nextBlockView[1][0].setBackgroundColor(color);        }        if(nextBlock.isRightUp()){            nextBlockView[2][0].setBackgroundColor(color);        }        if(nextBlock.isLeft()){            nextBlockView[0][1].setBackgroundColor(color);        }        if(nextBlock.isRight()){            nextBlockView[2][1].setBackgroundColor(color);        }        if(nextBlock.isDownLeft()){            nextBlockView[0][2].setBackgroundColor(color);        }        if(nextBlock.isDown()){            nextBlockView[1][2].setBackgroundColor(color);        }        if(nextBlock.isDownRight()){            nextBlockView[2][2].setBackgroundColor(color);        }    }    public void createDB(){        try {            mainDB = this.openOrCreateDatabase("Scoreboard",MODE_PRIVATE,null);            mainDB.execSQL("CREATE TABLE IF NOT EXISTS scoreboard1" + "(id integer primary key , name VARCHAR,score integer);");            File database = getApplicationContext().getDatabasePath("Scoreboard.db");            if(!database.exists()){                Toast.makeText(this, "DB create",Toast.LENGTH_SHORT).show();            }else{                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);                Toast.makeText(this, "DB missing",Toast.LENGTH_SHORT).show();            }        }catch (Exception e){        }    }}//Full visible == 1000 alpha