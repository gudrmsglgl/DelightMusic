package io.delight.player_api

import androidx.media3.common.MediaItem
import kotlinx.coroutines.flow.StateFlow


interface MusicPlayerManager {

    val playerState: StateFlow<PlayerState>

    fun connect()

    fun disconnect()

    fun playMedia(
        mediaId: String,
        uri: String,
        title: String? = null,
        artist: String? = null
    )

    fun play()

    fun togglePlayPause()
}
