package com.tetris.saar.tetris;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by saarsc on 18/10/2017.
 */

public class MusicThread extends Service implements MediaPlayer.OnCompletionListener {
    String musicSrc;
    MediaPlayer player;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent.getIntExtra("src",0) != 0){
            player = MediaPlayer.create(this, R.raw.tetrismusic);
        }else {
            musicSrc = intent.getStringExtra("src1");
            File song = new File(musicSrc);
            player = MediaPlayer.create(this, Uri.fromFile(song));
        }
        player.setLooping(true);
        player.setOnCompletionListener(this);
        player.start();
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
