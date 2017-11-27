package com.tetris.saar.tetris;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
//Main menu activity
public class MainMenu extends AppCompatActivity implements View.OnClickListener,Serializable{
    TextView tvHeader; // The header
    Button btnGame; // Go to the game
    Button btnScoreboard; // Go to scoreboard
    Button btnHowTo; // Go to how to play
    Button btnExit;
    Intent intent; // Main intent
    //Music handle
    Intent musicService;
    //Battery service
    Intent batteryService;

    ImageButton ibPickMusic;//Pick music
    //For the premmision
    int myPremmision;
    Context context;// This screen
    //Action Bar
    Menu mainMenu = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        context = this; // This screen
        // Request handler for external storage
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //Should the request be displayed
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                //request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        myPremmision);
            }
        }
        // Request handler for phone calls
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            //Should the request be displayed
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CALL_PHONE)) {
                //request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        myPremmision);
            }
        }
        //Music handle
        musicService = new Intent(this,MusicThread.class);
        musicService.putExtra("src",0);
        startService(musicService);
        //Battery handle
        batteryService = new Intent(this,BatteryService.class);
        startService(batteryService);
        //Syncing GUI with code
        ibPickMusic = (ImageButton) findViewById(R.id.ibPickMusic);
        tvHeader = (TextView)findViewById(R.id.tvHeader);
        btnGame = (Button) findViewById(R.id.btnGame);
        btnScoreboard = (Button) findViewById(R.id.btnScoreboard);
        btnHowTo = (Button) findViewById(R.id.btnHowTo);
        btnExit = (Button) findViewById(R.id.btnExit);
        //Click listeners
        ibPickMusic.setOnClickListener(this);
        btnGame.setOnClickListener(this);
        btnScoreboard.setOnClickListener(this);
        btnHowTo.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        //Creating the header
        String header ="<font color= '#ff0000'>T</font><font color='#ff8c00'>e</font><font color='#ffff00'>t</font><font color='#008000'>r</font><font color='#0000ff'>i</font><font color='#800080'>s</font>";
        tvHeader.setText(Html.fromHtml(header));
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
                Intent call= new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + ""));
                startActivity(call);
                break;
        }
        return true;
    }
    //Click listener for all the buttons
    @Override
    public void onClick(View v) {
        if(v.getId() == btnGame.getId()){
            intent = new Intent(this,GameActivity.class);
            intent.putExtra("service",musicService);
            startActivity(intent);
        }
        if(v.getId() == btnScoreboard.getId()){
            intent = new Intent(this,Scoreboard.class);
            startActivity(intent);
        }
        if(v.getId() == btnHowTo.getId()){
            intent = new Intent(this,HowToPlay.class);
            startActivity(intent);
        }
        if(v.getId() == ibPickMusic.getId()){
            changeMusic();
        }
        if(v.getId() == btnExit.getId()){
            System.gc();
            System.exit(0);
        }
    }
    //Changing the song
    public void changeMusic(){
        ArrayList<File> songList = getPlayList(Environment.getExternalStorageDirectory().getAbsolutePath() +"/Music"); //Hold all the song on the phone
        ArrayList<String> songName= new ArrayList<>(); // List of song names
        ArrayList<String> songPath = new ArrayList<>(); // List of song paths
        final ArrayList<String> copySongPath = new ArrayList<>(); //Backup of the songPath as a final
        //If there are songs
        if(songList!=null){
            //Split song list to song name and path lists
            for(int i=0;i<songList.size();i++){
                String fileName=songList.get(i).getName();
                String filePath=songList.get(i).getAbsolutePath();
                songName.add(fileName);
                songPath.add(filePath);
            }
        }
        copySongPath.addAll(songPath); // adding all the paths to the backup
        //Creating the dialog that display all the songs
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ListView lv = new ListView(this); //List view to hold the songs
        LinearLayout main = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        main.setLayoutParams(lp);
        //Inserting the list to the thread
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songName);
        lv.setAdapter(arrayAdapter);
        main.addView(lv);
        builder.setView(main);

        final AlertDialog alert = builder.create();
        alert.show();
        //Click listener for all the songs in the list view
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> list, View v, int pos, long id) {
                //Changing which song is playing
                stopService(musicService);
                musicService = new Intent(context, MusicThread.class);
                musicService.putExtra("src",1);
                musicService.putExtra("src1", Uri.parse(copySongPath.get(pos)).toString());
                startService(musicService);
                alert.dismiss();
            }

        });
    }
    //Get the list of the all the music files
    ArrayList<File> getPlayList(String rootPath) {
        ArrayList<File> fileList = new ArrayList<>(); // Holds the list of the songs
        File rootFolder = new File(rootPath); //The main folder
        File[] files = rootFolder.listFiles(); //All the files in the folder
        //If song add to the list if a directory go in side and add all the song there
            for (File file : files) {
                if (file.isDirectory()) {
                    if (getPlayList(file.getAbsolutePath()) != null) {
                        fileList.addAll(getPlayList(file.getAbsolutePath()));
                    } else {
                        break;
                    }
                    //Add only mp3 files
                } else if (file.getName().endsWith(".mp3")) {
                    fileList.add(file);
                }
            }
        return fileList;
    }
    @Override
    public void onBackPressed(){
    }
}
