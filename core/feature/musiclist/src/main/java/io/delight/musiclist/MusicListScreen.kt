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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import io.delight.component.EmptyMusicList
import io.delight.component.LoadingIndicator
import io.delight.core.feature.musiclist.R
import io.delight.designsystem.component.ErrorMessage
import io.delight.designsystem.component.MusicItemRow
import io.delight.designsystem.theme.DelightMusicTheme
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

@Preview(showBackground = true)
@Composable
private fun MusicListContentLoadingPreview() {
    DelightMusicTheme {
        MusicListContent(
            uiState = MusicListUiState(
                isLoading = true
            ),
            onMusicClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MusicListContentErrorPreview() {
    DelightMusicTheme {
        MusicListContent(
            uiState = MusicListUiState(
                isLoading = false,
                errorMessage = "에러가 발생했습니다."
            ),
            onMusicClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MusicListContentSuccessPreview() {
    DelightMusicTheme {
        MusicListContent(
            uiState = MusicListUiState(
                isLoading = false,
                musicList = listOf(
                    Music(
                        id = 1,
                        title = "Title 1",
                        artist = "Artist 1",
                        album = "Album 1",
                        albumId = 1,
                        durationMs = 1000,
                        fileUrl = "",
                        imageUrl = ""
                    ),
                    Music(
                        id = 2,
                        title = "Title 2",
                        artist = "Artist 2",
                        album = "Album 2",
                        albumId = 2,
                        durationMs = 2000,
                        fileUrl = "",
                        imageUrl = ""
                    )
                ),
                currentPlayingId = 1
            ),
            onMusicClick = {}
        )
    }
}
