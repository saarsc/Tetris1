package com.tetris.saar.tetris;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

/**
 * Created by saarsc on 23/10/2017.
 */

public class BatteryService extends Service {
    boolean run = true;
    Context context = this;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
                    BatteryManager bm = (BatteryManager) getSystemService(BATTERY_SERVICE);
                    int batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
                    if (batLevel < 95) {
                        NotificationCompat.Builder builder =
                                new NotificationCompat.Builder(this)
                                        .setSmallIcon(R.mipmap.yellow)
                                        .setContentTitle("My Notification Title")
                                        .setContentText("Something interesting happened");
                        int NOTIFICATION_ID = 12345;

                        Intent targetIntent = new Intent(this, MainMenu.class);
                        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        builder.setContentIntent(contentIntent);
                        NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        nManager.notify(NOTIFICATION_ID, builder.build());
                        run = false;
                    }
                }
            }


