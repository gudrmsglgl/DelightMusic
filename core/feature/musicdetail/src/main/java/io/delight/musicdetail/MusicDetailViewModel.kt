package io.delight.musicdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.delight.player_api.MediaControllerManager
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicDetailViewModel @Inject constructor(
    private val mediaControllerManager: MediaControllerManager
) : ViewModel() {

    private val actions = MutableSharedFlow<MusicDetailAction>().also { flow ->
        flow.onEach { action ->
            when (action) {
                is MusicDetailAction.PlayPause -> {
                    mediaControllerManager.togglePlayPause()
                }
                is MusicDetailAction.Previous -> {
                    mediaControllerManager.skipToPrevious()
                }
                is MusicDetailAction.Next -> {
                    mediaControllerManager.skipToNext()
                }
                is MusicDetailAction.SeekTo -> {
                    val duration = uiState.value.duration
                    if (duration > 0) {
                        val positionMs = (action.progress * duration).toLong()
                        mediaControllerManager.seekTo(positionMs)
                    }
                }
                is MusicDetailAction.ToggleShuffle -> {
                    mediaControllerManager.toggleShuffle()
                }
                is MusicDetailAction.CycleRepeatMode -> {
                    mediaControllerManager.cycleRepeatMode()
                }
            }
        }.launchIn(viewModelScope)
    }

    val uiState: StateFlow<MusicDetailUiState> = mediaControllerManager.playerState
        .map { playerState ->
            MusicDetailUiState(
                title = playerState.currentTitle ?: "",
                artist = playerState.currentArtist ?: "",
                album = playerState.currentAlbum ?: "",
                albumArtUri = playerState.currentAlbumArtUri,
                currentPosition = playerState.currentPosition,
                duration = playerState.duration,
                isPlaying = playerState.isPlaying,
                isBuffering = playerState.isBuffering,
                shuffleEnabled = playerState.shuffleEnabled,
                repeatMode = playerState.repeatMode
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MusicDetailUiState()
        )

    fun dispatch(action: MusicDetailAction) {
        viewModelScope.launch {
            actions.emit(action)
        }
    }
}
