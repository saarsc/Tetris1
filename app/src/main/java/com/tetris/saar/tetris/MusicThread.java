package com.tetris.saar.tetris;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import java.io.File;
//Class that control the music
public class MusicThread extends Service implements MediaPlayer.OnErrorListener {
    private final IBinder mBinder = new ServiceBinder(); // Used to binned the service to the activity
    MediaPlayer mPlayer = new MediaPlayer(); //Main MediaPlayer
    private int length = 0; // Where the song was paused to resume it later

    public MusicThread(){}

    //Enable to bind the service the activity
     public class ServiceBinder extends Binder {
        MusicThread getService() {
             return MusicThread.this;
        }
    }

    @Override
    public IBinder onBind(Intent arg0){return mBinder;}
    //Creating the basic player
    @Override
    public void onCreate (){
        super.onCreate();

        mPlayer = MediaPlayer.create( this, R.raw.tetrismusic);
        mPlayer.setOnErrorListener(this);
        if(mPlayer!= null)
        {
            mPlayer.setLooping(true);
            mPlayer.setVolume(100,100);
        }
        mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            public boolean onError(MediaPlayer mp, int what, int
                    extra){

                onError(mPlayer, what, extra);
                return true;
            }
        });
        mPlayer.start();
    }
    //Starting the player
    @Override
    public int onStartCommand (Intent intent, int flags, int startId)
    {
        if(mPlayer!= null){
            if(mPlayer.isPlaying()){
                mPlayer.start();
            }
        }

        return START_STICKY;
    }
    //Toggle Pause and UnPause the music
    public void toogleMusic()
    {
        if(mPlayer.isPlaying())
        {
            mPlayer.pause();
            length=mPlayer.getCurrentPosition();
        }else {
            mPlayer.seekTo(length);
            mPlayer.start();
        }
    }
    //Change the song based on a given string
    public void changeSong(String src){
        onDestroy();
        File song= new File(src);
        mPlayer = MediaPlayer.create( this,Uri.fromFile(song));
        mPlayer.setOnErrorListener(this);
        if(mPlayer!= null)
        {
            mPlayer.setLooping(true);
            mPlayer.setVolume(100,100);
        }
        mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            public boolean onError(MediaPlayer mp, int what, int
                    extra){

                onError(mPlayer, what, extra);
                return true;
            }
        });
    }
    //Stop the music
    public void stopMusic()
    {
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
    }
    //Kill the player
    @Override
    public void onDestroy ()
    {
        super.onDestroy();
        if(mPlayer != null)
        {
            try{
                mPlayer.stop();
                mPlayer.release();
            }finally {
                mPlayer = null;
            }
        }
    }
    //Display error when MediaPlayer does not work
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Toast.makeText( this, "music player failed", Toast.LENGTH_SHORT).show();
        if(mPlayer != null)
        {
            try{
                mPlayer.stop();
                mPlayer.release();
            }finally {
                mPlayer = null;
            }
        }
        return false;
    }
}
