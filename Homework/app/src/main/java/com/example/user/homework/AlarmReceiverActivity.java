package com.example.user.homework;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

public class AlarmReceiverActivity extends BroadcastReceiver {
    public static Ringtone ringtone;
    NotificationUtils mNotificationUtils;

    @Override
    public void onReceive(Context context, Intent intent) {
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();
        Notification.Builder nb = MainActivity.mNotificationUtils.
                getAndroidChannelNotificationTapable("ПРОСЫПАЙСЯ ЖЕ МЫ ГОРИМ");
        MainActivity.mNotificationUtils.getManager().notify(102, nb.build());
    }
}
