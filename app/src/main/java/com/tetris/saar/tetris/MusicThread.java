package com.tetris.saar.tetris;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;


import java.io.IOException;

/**
 * Created by saarsc on 18/10/2017.
 */

public class MusicThread extends Service implements MediaPlayer.OnCompletionListener {
    int musicSrc;
    MediaPlayer player;

    /*public MusicThread(int src) {
        musicSrc = src;
       *//* player = new MediaPlayer();
        try {
            player.setDataSource(src.getFileDescriptor(), src.getStartOffset(), src.getLength());
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.setLooping(true);*//*
    }*/

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        player = MediaPlayer.create(this, musicSrc);// raw/s.mp3
        player.setOnCompletionListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        musicSrc = intent.getIntExtra("src",getResources().getIdentifier("tetrismusic.mp3", "raw", getPackageName()));
        if (!player.isPlaying()) {
            player.start();
        }
        return START_STICKY;
    }

    public void onDestroy() {
        if (player.isPlaying()) {
            player.stop();
        }
        player.release();
    }

    public void onCompletion(MediaPlayer _mediaPlayer) {
        stopSelf();
    }

}
