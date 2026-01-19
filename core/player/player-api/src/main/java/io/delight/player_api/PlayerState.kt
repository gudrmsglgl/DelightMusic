package io.delight.player_api

import androidx.media3.common.MediaItem
import androidx.media3.common.Player

data class PlayerState(
    val isPlaying: Boolean = false,
    val currentMediaItem: MediaItem? = null,
    val currentPosition: Long = 0L,
    val duration: Long = 0L,
    val playbackState: Int = Player.STATE_IDLE,
    val isConnected: Boolean = false
) {
    val isBuffering: Boolean
        get() = playbackState == Player.STATE_BUFFERING

    val progress: Float
        get() = if (duration > 0) currentPosition.toFloat() / duration else 0f

    val currentMediaId: String?
        get() = currentMediaItem?.mediaId

    val currentTitle: String?
        get() = currentMediaItem?.mediaMetadata?.title?.toString()

    val currentArtist: String?
        get() = currentMediaItem?.mediaMetadata?.artist?.toString()

    val currentAlbumArtUri: String?
        get() = currentMediaItem?.mediaMetadata?.artworkUri?.toString()
}
