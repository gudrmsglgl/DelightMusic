package io.delight.player_api

import io.delight.player_api.model.MediaItemData
import kotlinx.coroutines.flow.Flow


interface MediaControllerManager {

    val playerState: Flow<PlayerState>

    fun playMedia(mediaItemData: MediaItemData)

    fun setPlaylistAndPlay(playlist: List<MediaItemData>, startIndex: Int = 0)

    fun play()

    fun pause()

    fun togglePlayPause()

    fun seekTo(positionMs: Long)

    fun skipToPrevious()

    fun skipToNext()

    fun toggleShuffle()

    fun cycleRepeatMode()
}


