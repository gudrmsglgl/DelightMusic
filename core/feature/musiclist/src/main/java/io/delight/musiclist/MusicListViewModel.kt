package io.delight.musiclist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.delight.domain_musiclist_api.GetMusicListUseCase
import io.delight.model.Music
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
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

    private val musicListFlow = flow { emitAll(getMusicListUseCase()) }
        .map<List<Music>, MusicListResult> { MusicListResult.Success(it) }
        .onStart { emit(MusicListResult.Loading) }
        .catch { emit(MusicListResult.Error(it)) }

    val uiState: StateFlow<MusicListUiState> = combine(
        musicListFlow,
        currentPlayingId
    ) { musicListResult, playingId ->
        when (musicListResult) {
            is MusicListResult.Loading -> MusicListUiState(isLoading = true)
            is MusicListResult.Error -> MusicListUiState(
                isLoading = false,
                errorMessage = musicListResult.error.message ?: "음악 목록을 불러오는데 실패했습니다."
            )
            is MusicListResult.Success -> MusicListUiState(
                musicList = musicListResult.musicList,
                isLoading = false,
                currentPlayingId = playingId
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MusicListUiState(isLoading = true)
    )

    fun onMusicClick(music: Music) {
        val updatedCurrentPlayingId =
            if (currentPlayingId.value == music.id) null
            else music.id

        currentPlayingId.update {
            updatedCurrentPlayingId
        }
    }
}
