package com.tetris.saar.tetris;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class MainMenu extends AppCompatActivity implements View.OnClickListener{
    TextView tvHeader;
    Button btnGame;
    Button btnScoreboard;
    Button btnHowTo;
    Intent intent;

    MusicThread musicThread;

    Intent musicService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        //Music handle
        int resID=getResources().getIdentifier("tetrismusic.mp3", "raw", getPackageName());


        musicService = new Intent(this,MusicThread.class);
        musicService.putExtra("src",resID);
        //bindService(musicService);
        startService(musicService);
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

    @Override
    public void onClick(View v) {
        if(v.getId() == btnGame.getId()){
            intent = new Intent(this,GameActivity.class);
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
    }
}
