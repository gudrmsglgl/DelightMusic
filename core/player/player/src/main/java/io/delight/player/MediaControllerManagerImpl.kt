package io.delight.player

import androidx.media3.common.Player
import androidx.media3.session.MediaController
import io.delight.player_api.MediaControllerManager
import io.delight.player_api.model.MediaItemData
import io.delight.player_api.model.toMediaItem
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaControllerManagerImpl @Inject constructor(
    connectedMediaController: ConnectedMediaController,
    mediaPlayerListener: MediaPlayerListener
) : MediaControllerManager {

    private var mediaController: MediaController? = null

    override val playerState = combine(
        connectedMediaController.mediaControllerFlow,
        mediaPlayerListener.playerStateFlow(),
        ::Pair
    ).map { (controller, playerState) ->
        mediaController = controller
        playerState
    }

    override fun playMedia(mediaItemData: MediaItemData) {
        mediaController?.apply {
            setMediaItem(mediaItemData.toMediaItem())
            prepare()
            play()
        }
    }

    override fun setPlaylistAndPlay(playlist: List<MediaItemData>, startIndex: Int) {
        if (playlist.isEmpty()) return

        mediaController?.apply {
            setMediaItems(
                playlist.map { it.toMediaItem() },
                startIndex,
                0L
            )
            prepare()
            play()
        }
    }

    override fun play() {
        mediaController?.play()
    }

    override fun pause() {
        mediaController?.pause()
    }

    override fun togglePlayPause() {
        mediaController?.let { controller ->
            if (controller.playWhenReady) {
                controller.pause()
            } else {
                controller.play()
            }
        }
    }

    override fun seekTo(positionMs: Long) {
        mediaController?.seekTo(positionMs)
    }

    override fun skipToPrevious() {
        mediaController?.let { controller ->
            if (controller.currentPosition > SKIP_PREVIOUS_THRESHOLD_MS) {
                controller.seekTo(0)
            } else if (controller.hasPreviousMediaItem()) {
                controller.seekToPreviousMediaItem()
            } else {
                controller.seekTo(0)
            }
        }
    }

    override fun skipToNext() {
        mediaController?.let { controller ->
            if (controller.hasNextMediaItem()) {
                controller.seekToNextMediaItem()
            }
        }
    }

    override fun toggleShuffle() {
        mediaController?.let { controller ->
            controller.shuffleModeEnabled = !controller.shuffleModeEnabled
        }
    }

    override fun cycleRepeatMode() {
        mediaController?.let { controller ->
            controller.repeatMode = when (controller.repeatMode) {
                Player.REPEAT_MODE_OFF -> Player.REPEAT_MODE_ALL
                Player.REPEAT_MODE_ALL -> Player.REPEAT_MODE_ONE
                else -> Player.REPEAT_MODE_OFF
            }
        }
    }

    companion object {
        private const val SKIP_PREVIOUS_THRESHOLD_MS = 3000L
    }
}
