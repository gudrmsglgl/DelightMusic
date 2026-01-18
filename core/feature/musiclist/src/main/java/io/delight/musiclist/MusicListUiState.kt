package io.delight.musiclist

import io.delight.model.Music

data class MusicListUiState(
    val musicList: List<Music> = emptyList(),
    val isLoading: Boolean = false,
    val isPermissionGranted: Boolean = false,
    val errorMessage: String? = null,
    val currentPlayingId: Long? = null,
    val isPlaying: Boolean = false
)

internal sealed interface MusicListResult {
    data object PermissionNotGranted : MusicListResult
    data object Loading : MusicListResult
    data class Success(val musicList: List<Music>) : MusicListResult
    data class Error(val error: Throwable) : MusicListResult
}

internal fun MusicListResult.toUiState(playbackState: PlaybackUiState) = when (this) {
    is MusicListResult.PermissionNotGranted -> MusicListUiState(
        isPermissionGranted = false
    )
    is MusicListResult.Loading -> MusicListUiState(
        isPermissionGranted = true,
        isLoading = true
    )
    is MusicListResult.Error -> MusicListUiState(
        isPermissionGranted = true,
        errorMessage = error.message ?: "음악 목록을 불러오는데 실패했습니다."
    )
    is MusicListResult.Success -> MusicListUiState(
        isPermissionGranted = true,
        musicList = musicList,
        currentPlayingId = playbackState.currentPlayingId,
        isPlaying = playbackState.isPlaying
    )
}