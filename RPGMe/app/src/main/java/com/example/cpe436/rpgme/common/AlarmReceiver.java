package com.example.cpe436.rpgme.common;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.cpe436.rpgme.R;
import com.example.cpe436.rpgme.controller.MainActivity;

/**
 * Sends a notification when receiving an alarm event
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager mNM = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);
        Notification.Builder builder = new Notification.Builder(context)
                .setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.nav_battle)
                .setContentTitle("RPG ME")
                .setContentText("Quest Reminder")
                .setSubText(intent.getStringExtra("NAME"));
        Notification notification = builder.build();
        mNM.notify(11, notification);
    }
}
