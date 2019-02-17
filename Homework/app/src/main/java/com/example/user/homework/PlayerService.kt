package com.example.user.homework

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.AudioManager.OnAudioFocusChangeListener
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.content.ContextCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.app.NotificationCompat.MediaStyle
import android.support.v4.media.session.MediaButtonReceiver
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat


class PlayerService : Service() {

    private val NOTIFICATION_ID = 404
    private val NOTIFICATION_DEFAULT_CHANNEL_ID = "default_channel"

    private val metadataBuilder = MediaMetadataCompat.Builder()

    private val stateBuilder = PlaybackStateCompat.Builder().setActions(
            PlaybackStateCompat.ACTION_PLAY
                    or PlaybackStateCompat.ACTION_STOP
                    or PlaybackStateCompat.ACTION_PAUSE
                    or PlaybackStateCompat.ACTION_PLAY_PAUSE
                    or PlaybackStateCompat.ACTION_SKIP_TO_NEXT
                    or PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
    )

    private var mediaSession: MediaSessionCompat? = null

    private var audioManager: AudioManager? = null
    private var audioFocusRequest: AudioFocusRequest? = null
    private var audioFocusRequested = false
    private var mediaPlayer: MediaPlayer? = null

    private val mediaSessionCallback = object : MediaSessionCompat.Callback() {

        internal var currentState = PlaybackStateCompat.STATE_STOPPED

        override fun onPlay() {
            if (currentState == PlaybackStateCompat.STATE_STOPPED) {
                startService(Intent(applicationContext, PlayerService::class.java))
                val track = musicSpace.current;
                updateMetadataFromTrack(track)
                mediaPlayer = MediaPlayer.create(baseContext, track.adress)
                mediaPlayer!!.start()
            }
            if (currentState == PlaybackStateCompat.STATE_PAUSED) {
                mediaPlayer!!.start()
            }
            val audioFocusResult: Int
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                audioFocusResult = audioManager!!.requestAudioFocus(audioFocusRequest!!)
            } else {
                audioFocusResult = audioManager!!.requestAudioFocus(audioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)
            }
            if (audioFocusResult != AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                return

            mediaSession!!.isActive = true // Сразу после получения фокуса


            mediaSession!!.setPlaybackState(stateBuilder.setState(PlaybackStateCompat.STATE_PLAYING, PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 1f).build())
            currentState = PlaybackStateCompat.STATE_PLAYING

            refreshNotificationAndForegroundStatus(currentState)
        }

        override fun onPause() {
            if (currentState == PlaybackStateCompat.STATE_PLAYING) {
                mediaPlayer!!.pause()
            }

            mediaSession!!.setPlaybackState(stateBuilder.setState(PlaybackStateCompat.STATE_PAUSED, PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 1f).build())
            currentState = PlaybackStateCompat.STATE_PAUSED

            refreshNotificationAndForegroundStatus(currentState)
        }

        override fun onStop() {
            if (currentState == PlaybackStateCompat.STATE_PLAYING) {
                mediaPlayer!!.stop()
            }
            if (audioFocusRequested) {
                audioFocusRequested = false

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    audioManager!!.abandonAudioFocusRequest(audioFocusRequest!!)
                } else {
                    audioManager!!.abandonAudioFocus(audioFocusChangeListener)
                }
            }
            mediaSession!!.isActive = false
            mediaSession!!.setPlaybackState(stateBuilder.setState(PlaybackStateCompat.STATE_STOPPED, PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 1f).build())
            currentState = PlaybackStateCompat.STATE_STOPPED

            refreshNotificationAndForegroundStatus(currentState)

            stopSelf()
        }

        override fun onSkipToNext() {
            val track = musicSpace.next;
            updateMetadataFromTrack(track)
            refreshNotificationAndForegroundStatus(currentState)
            if (currentState == PlaybackStateCompat.STATE_PLAYING || currentState == PlaybackStateCompat.STATE_PAUSED) {
                mediaPlayer!!.stop()
                mediaPlayer = MediaPlayer.create(baseContext, track.adress)
                mediaPlayer!!.start()
                mediaSession!!.setPlaybackState(stateBuilder.setState(PlaybackStateCompat.STATE_PLAYING, PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 1f).build())
                currentState = PlaybackStateCompat.STATE_PLAYING
            }
        }

        override fun onSkipToPrevious() {
            val track = musicSpace.previous;
            updateMetadataFromTrack(track)
            refreshNotificationAndForegroundStatus(currentState)
            if (currentState == PlaybackStateCompat.STATE_PLAYING || currentState == PlaybackStateCompat.STATE_PAUSED) {
                mediaPlayer!!.stop()
                mediaPlayer = MediaPlayer.create(baseContext, track.adress)
                mediaPlayer!!.start()
                mediaSession!!.setPlaybackState(stateBuilder.setState(PlaybackStateCompat.STATE_PLAYING, PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 1f).build())
                currentState = PlaybackStateCompat.STATE_PLAYING
            }
        }


        private fun updateMetadataFromTrack(track: MusicSpace.Track) {
            metadataBuilder.putString(MediaMetadataCompat.METADATA_KEY_TITLE, track.title)
            metadataBuilder.putString(MediaMetadataCompat.METADATA_KEY_ALBUM, track.artist)
            metadataBuilder.putString(MediaMetadataCompat.METADATA_KEY_ARTIST, track.artist)
            mediaSession!!.setMetadata(metadataBuilder.build())
        }
    }

    private val audioFocusChangeListener = OnAudioFocusChangeListener { focusChange ->
        when (focusChange) {
            AudioManager.AUDIOFOCUS_GAIN -> this.mediaSessionCallback.onPlay()
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> mediaSessionCallback.onPause()
            else -> mediaSessionCallback.onPause()
        }
        // Не очень красиво
    }

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(NOTIFICATION_DEFAULT_CHANNEL_ID, getString(R.string.notification_channel_name), IMPORTANCE_DEFAULT)
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)

            val audioAttributes = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            audioFocusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                    .setOnAudioFocusChangeListener(audioFocusChangeListener)
                    .setAcceptsDelayedFocusGain(false)
                    .setWillPauseWhenDucked(true)
                    .setAudioAttributes(audioAttributes)
                    .build()
        }
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        mediaSession = MediaSessionCompat(this, "PlayerService")
        mediaSession!!.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS)
        mediaSession!!.setCallback(mediaSessionCallback)

        val appContext = applicationContext

        val activityIntent = Intent(appContext, Information::class.java)
        mediaSession!!.setSessionActivity(PendingIntent.getActivity(appContext, 0, activityIntent, 0))

        val mediaButtonIntent = Intent(Intent.ACTION_MEDIA_BUTTON, null, appContext, MediaButtonReceiver::class.java)
        mediaSession!!.setMediaButtonReceiver(PendingIntent.getBroadcast(appContext, 0, mediaButtonIntent, 0))
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        MediaButtonReceiver.handleIntent(mediaSession, intent)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaSession!!.release()
    }


    override fun onBind(intent: Intent): IBinder? {
        return PlayerServiceBinder()
    }

    inner class PlayerServiceBinder : Binder() {
        val mediaSessionToken: MediaSessionCompat.Token
            get() = mediaSession!!.sessionToken
    }

    private fun refreshNotificationAndForegroundStatus(playbackState: Int) {
        when (playbackState) {
            PlaybackStateCompat.STATE_PLAYING -> {
                startForeground(NOTIFICATION_ID, getNotification(playbackState))
            }
            PlaybackStateCompat.STATE_PAUSED -> {
                NotificationManagerCompat.from(this@PlayerService).notify(NOTIFICATION_ID, getNotification(playbackState))
                stopForeground(false)
            }
            else -> {
                stopForeground(true)
            }
        }
    }

    private fun getNotification(playbackState: Int): Notification {
        val builder = MediaStyleHelper.from(this, mediaSession!!)
        builder.addAction(NotificationCompat.Action(android.R.drawable.ic_media_previous, getString(R.string.previous), MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS)))

        if (playbackState == PlaybackStateCompat.STATE_PLAYING)
            builder.addAction(NotificationCompat.Action(android.R.drawable.ic_media_pause, getString(R.string.pause), MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackStateCompat.ACTION_PLAY_PAUSE)))
        else
            builder.addAction(NotificationCompat.Action(android.R.drawable.ic_media_play, getString(R.string.play), MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackStateCompat.ACTION_PLAY_PAUSE)))

        builder.addAction(NotificationCompat.Action(android.R.drawable.ic_media_next, getString(R.string.next), MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackStateCompat.ACTION_SKIP_TO_NEXT)))
        builder.setStyle(MediaStyle()
                .setShowActionsInCompactView(1)
                .setShowCancelButton(true)
                .setCancelButtonIntent(MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackStateCompat.ACTION_STOP))
                .setMediaSession(mediaSession!!.sessionToken)) // setMediaSession требуется для Android Wear
        builder.setSmallIcon(R.mipmap.ic_launcher)
        builder.color = ContextCompat.getColor(this, R.color.colorPrimaryDark) // The whole background (in MediaStyle), not just icon background
        builder.setShowWhen(false)
        builder.priority = NotificationCompat.PRIORITY_HIGH
        builder.setOnlyAlertOnce(true)
        builder.setChannelId(NOTIFICATION_DEFAULT_CHANNEL_ID)

        return builder.build()
    }

    companion object {


        internal var musicSpace = MusicSpace()
    }
}