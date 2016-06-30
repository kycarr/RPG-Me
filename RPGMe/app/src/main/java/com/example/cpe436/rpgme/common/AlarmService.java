package com.example.cpe436.rpgme.common;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.Date;

/**
 * Creates an alarm at a given time that will send a notification to the phone
 */
public class AlarmService {
    public static void setAlarm(Context context, Date date, String name) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("NAME", name);
        PendingIntent mAlarmSender = PendingIntent.getBroadcast(context, 0, intent, 0);

        //Set the alarm to go off at the given time
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        long firstTime = c.getTimeInMillis();

        // Schedule the alarm!
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, firstTime, mAlarmSender);
    }
}