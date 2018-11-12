package com.example.user.homework;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Information extends AppCompatActivity {

    private PlayerService.PlayerServiceBinder playerServiceBinder;
    private MediaControllerCompat mediaController;
    private MediaControllerCompat.Callback callback;
    private ServiceConnection serviceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView playButton = findViewById(R.id.play);
        final ImageView pauseButton = findViewById(R.id.pause);
        final ImageView skipToNextButton = findViewById(R.id.skip_to_next);
        final Button skipToPreviousButton = findViewById(R.id.skip_to_previous);
        TextView artist = findViewById(R.id.artist);
        TextView title = findViewById(R.id.track);
        Intent intent = getIntent();
        artist.setText(intent.getStringExtra("name"));
        title.setText(intent.getStringExtra("surname"));
        callback = new MediaControllerCompat.Callback() {
            @Override
            public void onPlaybackStateChanged(PlaybackStateCompat state) {
                if (state == null)
                    return;
                boolean playing = state.getState() == PlaybackStateCompat.STATE_PLAYING;
                playButton.setEnabled(!playing);
                pauseButton.setEnabled(playing);
            }
        };

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                playerServiceBinder = (PlayerService.PlayerServiceBinder) service;
                try {
                    mediaController = new MediaControllerCompat(Information.this, playerServiceBinder.getMediaSessionToken());
                    mediaController.registerCallback(callback);
                    callback.onPlaybackStateChanged(mediaController.getPlaybackState());
                } catch (RemoteException e) {
                    mediaController = null;
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                playerServiceBinder = null;
                if (mediaController != null) {
                    mediaController.unregisterCallback(callback);
                    mediaController = null;
                }
            }
        };

        bindService(new Intent(this, PlayerService.class), serviceConnection, BIND_AUTO_CREATE);

        playButton.setOnClickListener(v -> {
            if (mediaController != null)
                mediaController.getTransportControls().play();
            playButton.setVisibility(View.INVISIBLE);
            pauseButton.setVisibility(View.VISIBLE);
        });

        pauseButton.setOnClickListener(v -> {
            if (mediaController != null)
                mediaController.getTransportControls().pause();
            pauseButton.setVisibility(View.INVISIBLE);
            playButton.setVisibility(View.VISIBLE);
        });

        skipToNextButton.setOnClickListener(v -> {
            if (mediaController != null)
                mediaController.getTransportControls().skipToNext();
            playButton.setVisibility(View.INVISIBLE);
            pauseButton.setVisibility(View.VISIBLE);
            artist.setText(PlayerService.musicSpace.getCurrent().getArtist());
            title.setText(PlayerService.musicSpace.getCurrent().getTitle());
        });

        skipToPreviousButton.setOnClickListener(v -> {
            if (mediaController != null)
                mediaController.getTransportControls().skipToPrevious();
            playButton.setVisibility(View.INVISIBLE);
            pauseButton.setVisibility(View.VISIBLE);
            artist.setText(PlayerService.musicSpace.getCurrent().getArtist());
            title.setText(PlayerService.musicSpace.getCurrent().getTitle());
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        playerServiceBinder = null;
        if (mediaController != null) {
            mediaController.unregisterCallback(callback);
            mediaController = null;
        }
        unbindService(serviceConnection);
        finish();
    }
}