package com.tetris.saar.tetris;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.felipecsl.gifimageview.library.GifImageView;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class SplashScreen extends AppCompatActivity {
    GifImageView gifImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        gifImageView = (GifImageView) findViewById(R.id.gifImageView);
        Thread timerThread = new Thread(){
            public void run(){
                try{
                    InputStream inputStream = getAssets().open("splashscreen.gif");
                    byte[] bytes = IOUtils.toByteArray(inputStream);
                    gifImageView.setBytes(bytes);
                    gifImageView.startAnimation();

                }
                catch(IOException ex)
                {

                }
                try{
                    sleep(3000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                } finally
                {
                    Intent intent = new Intent(SplashScreen.this,MainMenu.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}
