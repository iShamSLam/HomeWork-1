package com.example.user.homework;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;

public class NotificationUtils extends ContextWrapper {

    private NotificationManager mManager;
    public static final String ANDROID_CHANNEL_ID = "com.example.user.homework";
    public static final String ANDROID_CHANNEL_NAME = "ANDROID CHANNEL";

    public NotificationUtils(Context base) {
        super(base);
        createChannel();
    }

    public void createChannel() {

        // create android channel
        AudioAttributes.Builder attrs = new AudioAttributes.Builder();
        attrs.setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION);
        attrs.setUsage(AudioAttributes.USAGE_NOTIFICATION);
        NotificationChannel androidChannel = new NotificationChannel(ANDROID_CHANNEL_ID,
                ANDROID_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        androidChannel.enableLights(true);
        androidChannel.enableVibration(true);
        androidChannel.setLightColor(Color.GREEN);
        androidChannel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), attrs.build());
        androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        getManager().createNotificationChannel(androidChannel);
    }

    NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public Notification.Builder getAndroidChannelNotificationTapable(String text) {
        Intent intent = new Intent(this, AwakeningActivity.class);
        PendingIntent awakening = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return new Notification.Builder(getApplicationContext(), NotificationUtils.ANDROID_CHANNEL_ID)
                .setContentTitle(text)
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                .setAutoCancel(true)
                .setContentIntent(awakening);
    }

    public Notification.Builder getAndroidChannelNotification(String text) {
        return new Notification.Builder(getApplicationContext(), NotificationUtils.ANDROID_CHANNEL_ID)
                .setContentTitle(text)
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                .setAutoCancel(true);
    }
}
