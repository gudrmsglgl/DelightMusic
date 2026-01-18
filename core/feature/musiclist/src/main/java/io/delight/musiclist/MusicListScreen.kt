package io.delight.musiclist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import io.delight.component.EmptyMusicList
import io.delight.component.LoadingIndicator
import io.delight.core.feature.musiclist.R
import io.delight.designsystem.component.ErrorMessage
import io.delight.designsystem.component.MusicItemRow
import io.delight.model.Music
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicListScreen(
    viewModel: MusicListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.music_list_title)) }
            )
        }
    ) { paddingValues ->
        MusicListContent(
            uiState = uiState,
            onMusicClick = viewModel::onMusicClick,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun MusicListContent(
    uiState: MusicListUiState,
    onMusicClick: (Music) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        when {
            uiState.isLoading -> LoadingIndicator()
            uiState.errorMessage != null -> {
                ErrorMessage(
                    message = uiState.errorMessage,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            uiState.musicList.isEmpty() -> EmptyMusicList()
            else -> MusicList(
                musicList = uiState.musicList.toImmutableList(),
                currentPlayingId = uiState.currentPlayingId,
                onMusicClick = onMusicClick
            )
        }
    }
}

@Composable
private fun MusicList(
    musicList: ImmutableList<Music>,
    currentPlayingId: Long?,
    onMusicClick: (Music) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            items = musicList,
            key = { it.id }
        ) { music ->
            MusicItemRow(
                title = music.title,
                artist = music.artist,
                albumArtUri = music.imageUrl.ifEmpty { null },
                isPlaying = currentPlayingId == music.id,
                onClick = { onMusicClick(music) }
            )
        }
    }
}