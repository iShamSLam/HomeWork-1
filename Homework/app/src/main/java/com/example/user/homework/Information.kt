package com.example.user.homework

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class Information : AppCompatActivity() {

    private var playerServiceBinder: PlayerService.PlayerServiceBinder? = null
    private var mediaController: MediaControllerCompat? = null
    private var callback: MediaControllerCompat.Callback? = null
    private var serviceConnection: ServiceConnection? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val playButton = findViewById<ImageView>(R.id.play)
        val pauseButton = findViewById<ImageView>(R.id.pause)
        val skipToNextButton = findViewById<ImageView>(R.id.skip_to_next)
        val skipToPreviousButton = findViewById<Button>(R.id.skip_to_previous)
        val artist = findViewById<TextView>(R.id.artist)
        val title = findViewById<TextView>(R.id.track)
        val intent = intent
        artist.text = intent.getStringExtra("name")
        title.text = intent.getStringExtra("surname")
        callback = object : MediaControllerCompat.Callback() {
            override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
                if (state == null)
                    return
                val playing = state.state == PlaybackStateCompat.STATE_PLAYING
                playButton.isEnabled = !playing
                pauseButton.isEnabled = playing
            }
        }

        serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                playerServiceBinder = service as PlayerService.PlayerServiceBinder
                try {
                    mediaController = MediaControllerCompat(this@Information, playerServiceBinder!!.mediaSessionToken)
                    mediaController!!.registerCallback(callback!!)
                    callback!!.onPlaybackStateChanged(mediaController!!.playbackState)
                } catch (e: RemoteException) {
                    mediaController = null
                }

            }

            override fun onServiceDisconnected(name: ComponentName) {
                playerServiceBinder = null
                if (mediaController != null) {
                    mediaController!!.unregisterCallback(callback!!)
                    mediaController = null
                }
            }
        }

        bindService(Intent(this, PlayerService::class.java), serviceConnection, Context.BIND_AUTO_CREATE)

        playButton.setOnClickListener {
            if (mediaController != null)
                mediaController!!.transportControls.play()
            playButton.visibility = View.INVISIBLE
            pauseButton.visibility = View.VISIBLE
        }

        pauseButton.setOnClickListener { v ->
            if (mediaController != null)
                mediaController!!.transportControls.pause()
            pauseButton.visibility = View.INVISIBLE
            playButton.visibility = View.VISIBLE
        }

        skipToNextButton.setOnClickListener { v ->
            if (mediaController != null)
                mediaController!!.transportControls.skipToNext()
            playButton.visibility = View.INVISIBLE
            pauseButton.visibility = View.VISIBLE
            artist.text = PlayerService.musicSpace.current.artist
            title.text = PlayerService.musicSpace.current.title
        }

        skipToPreviousButton.setOnClickListener { v ->
            if (mediaController != null)
                mediaController!!.transportControls.skipToPrevious()
            playButton.visibility = View.INVISIBLE
            pauseButton.visibility = View.VISIBLE
            artist.text = PlayerService.musicSpace.current.artist
            title.text = PlayerService.musicSpace.current.title
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        playerServiceBinder = null
        if (mediaController != null) {
            mediaController!!.unregisterCallback(callback!!)
            mediaController = null
        }
        unbindService(serviceConnection)
        finish()
    }
}