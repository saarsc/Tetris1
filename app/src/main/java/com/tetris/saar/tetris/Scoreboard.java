package com.tetris.saar.tetris;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Scoreboard extends AppCompatActivity  implements View.OnClickListener{
    //Buttons
    Button btnScore;
    Button btnName;
   int order=0;
    //ListView
    ListView lvScore;
    ArrayList<String> displayDB = new ArrayList<>();
    //Database
    Databasehelper mainDB;
    Cursor cursor;
    //Menu
    Menu mainMenu = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        btnScore = (Button) findViewById(R.id.btnScore);
        btnName = (Button) findViewById(R.id.btnName);
        btnScore.setOnClickListener(this);
        btnName.setOnClickListener(this);

        lvScore = (ListView) findViewById(R.id.lvScoreboard);

        mainDB = new Databasehelper(this);
        displayList();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == btnScore.getId()){
            if(order !=0){
                btnScore.setText("Score ▼");
                order =0;
            }else {
                    btnScore.setText("Score ▲");
                    order = 1;
            }
            btnName.setText("Name");
        }
        if(v.getId() == btnName.getId()){
            if(order !=2){
                btnName.setText("Name ▼");
                order = 2;

            }else {
                btnName.setText("Name ▲");
                order = 3;

            }
            btnScore.setText("Score");
        }
        displayList();
    }
    public void displayList(){
        displayDB.clear();
        //Getting the data from the database
        displayDB.addAll(mainDB.displayList(order));
        if(!displayDB.isEmpty()){
            ArrayAdapter<String> adpater = new ArrayAdapter<String>(this, R.layout.listviewlayout,displayDB);
            lvScore.setAdapter(adpater);
        }
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
    public void onBackPressed(){
        Intent intent = new Intent(this,MainMenu.class);
        startActivity(intent);
    }
}
