package io.delight.musicdetail

import androidx.compose.runtime.Immutable
import io.delight.player_api.RepeatMode

@Immutable
data class MusicDetailUiState(
    val title: String = "",
    val artist: String = "",
    val album: String = "",
    val albumArtUri: String? = null,
    val currentPosition: Long = 0L,
    val duration: Long = 0L,
    val isPlaying: Boolean = false,
    val isBuffering: Boolean = false,
    val hasNext: Boolean = false,
    val hasPrevious: Boolean = false,
    val shuffleEnabled: Boolean = false,
    val repeatMode: RepeatMode = RepeatMode.OFF
) {
    val progress: Float
        get() = if (duration > 0) currentPosition.toFloat() / duration else 0f
}
