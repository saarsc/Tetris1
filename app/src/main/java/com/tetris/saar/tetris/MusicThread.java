package com.tetris.saar.tetris;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;

import java.io.File;
//Class that control the music
public class MusicThread extends Service implements MediaPlayer.OnCompletionListener {
    String musicSrc; // Which file to use
    MediaPlayer player; //The mediaplayer
    static boolean isPlaying = false;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

    }
    //Control the music
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!isPlaying) {
            //Should you use the default song
            if (intent.getIntExtra("src", 0) == 0) {
                player = MediaPlayer.create(this, R.raw.tetrismusic);
                //Change the song to the picked one
            } else {
                musicSrc = intent.getStringExtra("src1");
                File song = new File(musicSrc);
                player = MediaPlayer.create(this, Uri.fromFile(song));
            }
            //Setting the media player
            player.setLooping(true);
            player.setOnCompletionListener(this);
            player.start();
        }
        return START_STICKY;
    }
    //Kill the service
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
