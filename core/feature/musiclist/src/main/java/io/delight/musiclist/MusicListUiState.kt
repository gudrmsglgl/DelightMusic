package io.delight.musiclist

import io.delight.model.Music

data class MusicListUiState(
    val musicList: List<Music> = emptyList(),
    val isLoading: Boolean = false,
    val isPermissionGranted: Boolean = false,
    val errorMessage: String? = null,
    val currentPlayingId: Long? = null
)

internal sealed interface MusicListResult {
    data object PermissionNotGranted : MusicListResult
    data object Loading : MusicListResult
    data class Success(val musicList: List<Music>) : MusicListResult
    data class Error(val error: Throwable) : MusicListResult
}