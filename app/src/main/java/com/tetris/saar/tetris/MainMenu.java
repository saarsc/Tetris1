package com.tetris.saar.tetris;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.zip.Inflater;

public class MainMenu extends AppCompatActivity implements View.OnClickListener,Serializable{
    TextView tvHeader;
    Button btnGame;
    Button btnScoreboard;
    Button btnHowTo;
    Intent intent;
    //Music handle
    Intent musicService;
    ImageButton ibPickMusic;
    int myPremmision;
    Context context;
    //Action Bar
    Menu mainMenu = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        context = this;
        // Request handler
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        myPremmision);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        // Request handler
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CALL_PHONE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        myPremmision);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        //Music handle
        startService(musicService);
        ibPickMusic = (ImageButton) findViewById(R.id.ibPickMusic);
        ibPickMusic.setOnClickListener(this);
        //Syncing GUI with code
        tvHeader = (TextView)findViewById(R.id.tvHeader);
        btnGame = (Button) findViewById(R.id.btnGame);
        btnScoreboard = (Button) findViewById(R.id.btnScoreboard);
        btnHowTo = (Button) findViewById(R.id.btnHowTo);
        //Click listeners
        btnGame.setOnClickListener(this);
        btnScoreboard.setOnClickListener(this);
        btnHowTo.setOnClickListener(this);
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
            mainMenu.performIdentifierAction(R.id.music, 0);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    //Click listener
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch(item.getItemId()){
            case R.id.music:
                changeMusic();
                break;
        }
        return true;
    }
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
           // ArrayList<HashMap<String,String>> songList=getPlayList(Environment.getExternalStorageDirectory().getAbsolutePath() +"/Music");
            changeMusic();
        }
    }
    public void changeMusic(){
        ArrayList<File> songList = getPlayList(Environment.getExternalStorageDirectory().getAbsolutePath() +"/Music");
        ArrayList<String> songName= new ArrayList<>();
        ArrayList<String> songPath = new ArrayList<>();
        final ArrayList<String> copySongPath = new ArrayList<>();
        if(songList!=null){
            for(int i=0;i<songList.size();i++){
                String fileName=songList.get(i).getName();
                String filePath=songList.get(i).getAbsolutePath();
                songName.add(fileName);
                songPath.add(filePath);
            }
        }
        copySongPath.addAll(songPath);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ListView lv = new ListView(this);
        LinearLayout main = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        main.setLayoutParams(lp);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songName);
        lv.setAdapter(arrayAdapter);
        main.addView(lv);
        builder.setView(main);

        final AlertDialog alert = builder.create();
        alert.show();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> list, View v, int pos, long id) {
                stopService(musicService);
                musicService = new Intent(context, MusicThread.class);
                musicService.putExtra("src",0);
                musicService.putExtra("src1", Uri.parse(copySongPath.get(pos)).toString());
                startService(musicService);
                alert.dismiss();
            }

        });
    }
    //Get the list of the all the music files
    ArrayList<File> getPlayList(String rootPath) {
        ArrayList<File> fileList = new ArrayList<>();
        File rootFolder = new File(rootPath);
        File[] files = rootFolder.listFiles(); //here you will get NPE if directory doesn't contains  any file,handle it like this.
            for (File file : files) {
                if (file.isDirectory()) {
                    if (getPlayList(file.getAbsolutePath()) != null) {
                        fileList.addAll(getPlayList(file.getAbsolutePath()));
                    } else {
                        break;
                    }
                } else if (file.getName().endsWith(".mp3")) {
                    /*HashMap<String, String> song = new HashMap<>();
                    song.put("file_path", file.getAbsolutePath());
                    song.put("file_name", file.getName());*/
                    fileList.add(file);
                }
            }
        return fileList;
    }

}
