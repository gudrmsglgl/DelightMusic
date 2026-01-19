package io.delight.player.di

import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaController
import androidx.media3.session.MediaSession
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.delight.player.ConnectedMediaController
import io.delight.player.ConnectedMediaControllerImpl
import io.delight.player.MediaControllerManagerImpl
import io.delight.player.MediaPlayerListener
import io.delight.player.MediaPlayerListenerImpl
import io.delight.player.MediaSessionCallback
import io.delight.player.PlaybackService
import io.delight.player_api.MediaControllerManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PlayerModule {

    @Binds
    @Singleton
    abstract fun bindsMusicPlayerManager(
        mediaControllerManagerImpl: MediaControllerManagerImpl
    ): MediaControllerManager

    @Binds
    abstract fun bindsConnectedMediaController(
        connectedMediaControllerImpl: ConnectedMediaControllerImpl
    ): ConnectedMediaController

    @Binds
    abstract fun bindsMediaPlayerListener(
        mediaPlayerListenerImpl: MediaPlayerListenerImpl
    ): MediaPlayerListener


    companion object {

        @Singleton
        @Provides
        fun providesExoPlayer(
            @ApplicationContext context: Context
        ): ExoPlayer {
            return ExoPlayer.Builder(context)
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
                        .setUsage(C.USAGE_MEDIA)
                        .build(),
                    true
                )
                .build()
        }

        @Singleton
        @Provides
        fun providesMediaSession(
            @ApplicationContext context: Context,
            player: ExoPlayer,
            callback: MediaSessionCallback
        ): MediaSession {
            val sessionActivityPendingIntent = context.packageManager
                .getLaunchIntentForPackage(context.packageName)
                ?.let { intent ->
                    PendingIntent.getActivity(
                        context,
                        0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )
                }

            return MediaSession.Builder(context, player)
                .setCallback(callback)
                .apply {
                    sessionActivityPendingIntent?.let { setSessionActivity(it) }
                }
                .build()
        }

        @Singleton
        @Provides
        fun providesSessionToken(
            @ApplicationContext context: Context
        ): SessionToken {
            return SessionToken(context, ComponentName(context, PlaybackService::class.java))
        }

        @Singleton
        @Provides
        fun providesListenableFutureMediaController(
            @ApplicationContext context: Context,
            sessionToken: SessionToken
        ): ListenableFuture<MediaController> {
            return MediaController.Builder(context, sessionToken).buildAsync()
        }

    }
}
