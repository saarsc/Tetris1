package com.tetris.saar.tetris;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
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
public class BatteryService extends BroadcastReceiver{

    boolean ran = false; // Don't spam the notification. If notification was already sent
    @Override
    public void onReceive(Context context, Intent intent) {
        if(!ran){
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0); // Getting the level
            if(level < 92) {
                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(context)
                                .setSmallIcon(R.mipmap.yellow)
                                .setContentTitle("Low Battery!")
                                .setContentText("Your battery is under 92% D:");
                int NOTIFICATION_ID = 12345;
                Intent targetIntent = new Intent(context, MainMenu.class);
                PendingIntent contentIntent = PendingIntent.getActivity(context, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(contentIntent);
                NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                nManager.notify(NOTIFICATION_ID, builder.build());
                ran = true;
            }
        }
    }
}


