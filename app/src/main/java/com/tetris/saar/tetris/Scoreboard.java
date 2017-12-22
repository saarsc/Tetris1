package com.tetris.saar.tetris;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
//Scoreboard activity
public class Scoreboard extends AppCompatActivity{
    //Buttons
    Spinner nameSpinner;
    Spinner scoreSpinner;
   int order=0; //In which order to show the database
    //ListView
    ListView lvScore;
    ListView lvName;
    //Data holders
    ArrayList<ArrayList<String>> displayDB = new ArrayList<>(); //Hold the list to display
    ArrayList<String> displayName = new ArrayList<>();
    ArrayList<String> displayScore = new ArrayList<>();
    //Database
    Databasehelper mainDB;
    //Menu
    Menu mainMenu = null;
    //Context
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        context = this;
        //Spinners
        nameSpinner = (Spinner) findViewById(R.id.nameSpinner);
        scoreSpinner = (Spinner) findViewById(R.id.scoreSpinner);
        //Spinners text
        String[] nameOptions = {"Name","Descending","Ascending"};
        String[] scoreOptions = {"Score","Descending","Ascending"};
        //Spinners adapters
        ArrayAdapter<CharSequence> nameAdapter =new ArrayAdapter(this,R.layout.spinner_text,nameOptions);
        ArrayAdapter<CharSequence> scoreAdapter =new ArrayAdapter(this,R.layout.spinner_text,scoreOptions);
        //Selected item code
        scoreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selcted = scoreSpinner.getSelectedItem().toString();
                if(selcted.equals("Descending")){
                    order = 0;
                }else{
                    order=1;
                }
                displayList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        nameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selcted = nameSpinner.getSelectedItem().toString();
                if(selcted.equals("Descending")){
                    order = 2;
                }else{
                    order=3;
                }
                displayList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Setting the adapters
        nameSpinner.setAdapter(nameAdapter);
        scoreSpinner.setAdapter(scoreAdapter);
       //List Views
        lvScore = (ListView) findViewById(R.id.lvScore);
        lvName = (ListView) findViewById(R.id.lvName);
        mainDB = new Databasehelper(this);
        displayList();
        //Remove Entry

    }
    //Displaying the list
    public void displayList() {
        displayDB.clear(); // Clearing the old list
        //Getting the data from the database by the order
        displayDB.addAll(mainDB.displayList(order));
        //Displaying the list
        if (!displayDB.isEmpty()) {
            final ArrayAdapter nameAdpater = new ArrayAdapter<>(this, R.layout.listviewlayout, displayDB.get(0));
            lvName.setAdapter(nameAdpater);
            final ArrayAdapter  scoreAdpater = new ArrayAdapter<>(this, R.layout.listviewlayout, displayDB.get(1));
            lvScore.setAdapter(scoreAdpater);
            //Removing items
            lvName.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
            {
                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                    Databasehelper.callRemove(displayDB.get(2).get(pos),context);
                    displayList();
                    return true;
                }
            });
            lvScore.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
            {
                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                    Databasehelper.callRemove(displayDB.get(2).get(pos),context);
                    displayList();
                    return true;
                }
            });
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
