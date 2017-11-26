package com.tetris.saar.tetris;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

/**
 * Created by saarsc on 23/10/2017.
 */
//Low battery notification control
public class BatteryService extends Service {
    boolean run = true;
    Context context = this;
    private final Handler handler = new Handler();
    BatteryManager bm;
    int batLevel;
    private final Runnable refresher = new Runnable() {
        public void run() {
            bm = (BatteryManager) getSystemService(BATTERY_SERVICE);
            batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
            if (batLevel <= 90) {
                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(context)
                                .setSmallIcon(R.mipmap.yellow)
                                .setContentTitle("Low Battery!")
                                .setContentText("Your battery is under 92% D:");
                int NOTIFICATION_ID = 12345;
                Intent targetIntent = new Intent(context, MainMenu.class);
                PendingIntent contentIntent = PendingIntent.getActivity(context, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(contentIntent);
                NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                nManager.notify(NOTIFICATION_ID, builder.build());
                run = false;
            }
        }
    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        context = this;
        handler.postDelayed(refresher,9000);
    }
    @Override
    public void onDestroy(){
        handler.removeCallbacks(refresher);
    }
}


