package io.delight.player_api

import androidx.media3.common.MediaItem
import androidx.media3.common.Player

enum class RepeatMode {
    OFF,
    ONE,
    ALL;

    companion object {
        fun fromPlayer(repeatMode: Int): RepeatMode = when (repeatMode) {
            Player.REPEAT_MODE_ONE -> ONE
            Player.REPEAT_MODE_ALL -> ALL
            else -> OFF
        }
    }
}

data class PlayerState(
    val isPlaying: Boolean = false,
    val currentMediaItem: MediaItem? = null,
    val currentPosition: Long = 0L,
    val duration: Long = 0L,
    val playbackState: Int = Player.STATE_IDLE,
    val isConnected: Boolean = false,
    val shuffleEnabled: Boolean = false,
    val repeatMode: RepeatMode = RepeatMode.OFF
) {
    val isBuffering: Boolean
        get() = playbackState == Player.STATE_BUFFERING

    val currentMediaId: String?
        get() = currentMediaItem?.mediaId

    val currentTitle: String?
        get() = currentMediaItem?.mediaMetadata?.title?.toString()

    val currentArtist: String?
        get() = currentMediaItem?.mediaMetadata?.artist?.toString()

    val currentAlbum: String?
        get() = currentMediaItem?.mediaMetadata?.albumTitle?.toString()

    val currentAlbumArtUri: String?
        get() = currentMediaItem?.mediaMetadata?.artworkUri?.toString()
}
