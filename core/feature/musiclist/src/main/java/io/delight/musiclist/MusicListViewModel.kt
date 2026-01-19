package io.delight.musiclist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.delight.domain_musiclist_api.GetMusicListUseCase
import io.delight.model.Music
import io.delight.player_api.MediaControllerManager
import io.delight.player_api.model.MediaItemData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MusicListViewModel @Inject constructor(
    private val getMusicListUseCase: GetMusicListUseCase,
    private val mediaControllerManager: MediaControllerManager
) : ViewModel() {

    private val isPermissionGranted = MutableStateFlow(false)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val musicListFlow = isPermissionGranted.flatMapLatest { granted ->
        if (granted) {
            flow { emitAll(getMusicListUseCase()) }
                .map<List<Music>, MusicListResult> { MusicListResult.Success(it) }
                .onStart { emit(MusicListResult.Loading) }
                .catch { emit(MusicListResult.Error(it)) }
        } else {
            flowOf(MusicListResult.PermissionNotGranted)
        }
    }

    private val playbackUiState = mediaControllerManager
        .playerState
        .map { PlaybackUiState(it.currentMediaId?.toLongOrNull(), it.isPlaying) }
        .distinctUntilChanged()

    val uiState: StateFlow<MusicListUiState> = combine(
        musicListFlow,
        playbackUiState,
        transform = MusicListResult::toUiState
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MusicListUiState(isLoading = false, isPermissionGranted = false)
    )

    fun onPermissionResult(isGranted: Boolean) {
        isPermissionGranted.update {
            isGranted
        }
    }

    fun onMusicClick(music: Music) {
        val currentPlayingId = uiState.value.currentPlayingId

        if (currentPlayingId != music.id) {
            val currentMusicList = uiState.value.musicList
            val startIndex = currentMusicList
                .indexOfFirst { it.id == music.id }
                .takeIf { it >= 0 }
                ?: 0

            val playlist = currentMusicList.map { it.toMediaItemData() }
            mediaControllerManager.setPlaylistAndPlay(playlist, startIndex)
        }
    }
    
    private fun Music.toMediaItemData() = MediaItemData(
        mediaId = id.toString(),
        uri = fileUrl,
        title = title,
        artist = artist,
        album = album,
        albumArtUri = imageUrl.ifEmpty { null }
    )

}

data class PlaybackUiState(
    val currentPlayingId: Long?,
    val isPlaying: Boolean
)