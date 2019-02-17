package com.example.user.homework;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import static android.support.v4.content.WakefulBroadcastReceiver.startWakefulService;

public class MainActivity extends AppCompatActivity {

    Button alarm_start, alarm_stop;
    TextView alarm_status;
    TimePicker timePicker;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    static NotificationUtils mNotificationUtils;
    PowerManager pm;
    PowerManager.WakeLock wakeLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarm_start = findViewById(R.id.btn_alarm_start);
        alarm_stop = findViewById(R.id.btn_alarm_stop);
        alarm_status = findViewById(R.id.tv_alarm_status);
        pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock((PowerManager.PARTIAL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "MyApp:wake");
        wakeLock.acquire();
        timePicker = findViewById(R.id.time_picker);
        Calendar calendar = Calendar.getInstance();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        mNotificationUtils = new NotificationUtils(this);
        Intent alarmIntent = new Intent(this, AlarmReceiverActivity.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        alarm_start.setOnClickListener(v -> {
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
            calendar.set(Calendar.MINUTE, timePicker.getMinute());
            String minute;
            if (timePicker.getMinute() < 10)
                minute = String.valueOf("0" + timePicker.getMinute());
            else minute = String.valueOf(timePicker.getMinute());
            setTimeText("Будильник запущен на "
                    + String.valueOf(timePicker.getHour()
                    + ":" + minute));
            Notification.Builder nb = mNotificationUtils.
                    getAndroidChannelNotification(alarm_status.getText().toString());
            mNotificationUtils.getManager().notify(101, nb.build());
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        });
        alarm_stop.setOnClickListener(v -> {
            setTimeText("Будильник остановлен");
            alarmManager.cancel(pendingIntent);
        });
    }

    private void setTimeText(String text) {
        alarm_status.setText(text);
    }
}
