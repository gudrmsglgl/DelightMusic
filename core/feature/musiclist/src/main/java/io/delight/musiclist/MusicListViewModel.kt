package io.delight.musiclist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.delight.domain_musiclist_api.GetMusicListUseCase
import io.delight.model.Music
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
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
    private val getMusicListUseCase: GetMusicListUseCase
) : ViewModel() {

    private val currentPlayingId = MutableStateFlow<Long?>(null)
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

    val uiState: StateFlow<MusicListUiState> = combine(
        musicListFlow,
        currentPlayingId
    ) { musicListResult, playingId ->
        when (musicListResult) {
            is MusicListResult.PermissionNotGranted -> MusicListUiState(
                isPermissionGranted = false,
                isLoading = false
            )
            is MusicListResult.Loading -> MusicListUiState(
                isPermissionGranted = true,
                isLoading = true
            )
            is MusicListResult.Error -> MusicListUiState(
                isPermissionGranted = true,
                isLoading = false,
                errorMessage = musicListResult.error.message ?: "음악 목록을 불러오는데 실패했습니다."
            )
            is MusicListResult.Success -> MusicListUiState(
                isPermissionGranted = true,
                musicList = musicListResult.musicList,
                isLoading = false,
                currentPlayingId = playingId
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MusicListUiState(isLoading = false, isPermissionGranted = false)
    )

    fun onPermissionResult(isGranted: Boolean) {
        isPermissionGranted.value = isGranted
    }

    fun onMusicClick(music: Music) {
        val updatedCurrentPlayingId =
            if (currentPlayingId.value == music.id) null
            else music.id

        currentPlayingId.update {
            updatedCurrentPlayingId
        }
    }
}
