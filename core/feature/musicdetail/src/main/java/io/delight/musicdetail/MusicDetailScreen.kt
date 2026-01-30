package io.delight.musicdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.delight.components.AlbumArtwork
import io.delight.components.MusicController
import io.delight.components.MusicDetailTopBar
import io.delight.components.MusicInfo
import io.delight.components.MusicProgressBar
import io.delight.designsystem.theme.DelightMusicTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicDetailScreen(
    viewModel: MusicDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            MusicDetailTopBar(
                onBackClick = viewModel::onBack
            )
        }
    ) { paddingValues ->
        MusicDetailContent(
            uiState = uiState,
            onAction = viewModel::dispatch,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun MusicDetailContent(
    uiState: MusicDetailUiState,
    onAction: (MusicDetailAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val gradientColors = listOf(
        MaterialTheme.colorScheme.surface,
        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
        MaterialTheme.colorScheme.surface
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(colors = gradientColors)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            AlbumArtwork(
                albumArtUri = uiState.albumArtUri,
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .aspectRatio(1f)
            )

            Spacer(modifier = Modifier.height(32.dp))

            MusicInfo(
                title = uiState.title,
                artist = uiState.artist,
                album = uiState.album
            )

            Spacer(modifier = Modifier.height(24.dp))

            MusicProgressBar(
                currentPosition = uiState.currentPosition,
                duration = uiState.duration,
                progress = uiState.progress,
                onProgressChange = { onAction(MusicDetailAction.SeekTo(it)) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            MusicController(
                isPlaying = uiState.isPlaying,
                isBuffering = uiState.isBuffering,
                shuffleEnabled = uiState.shuffleEnabled,
                repeatMode = uiState.repeatMode,
                onPlayPauseClick = { onAction(MusicDetailAction.PlayPause) },
                onPreviousClick = { onAction(MusicDetailAction.Previous) },
                onNextClick = { onAction(MusicDetailAction.Next) },
                onShuffleClick = { onAction(MusicDetailAction.ToggleShuffle) },
                onRepeatClick = { onAction(MusicDetailAction.CycleRepeatMode) }
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MusicDetailContentPreview() {
    DelightMusicTheme {
        MusicDetailContent(
            uiState = MusicDetailUiState(
                title = "좋은 노래 제목",
                artist = "멋진 아티스트",
                album = "앨범 이름",
                currentPosition = 60000,
                duration = 180000,
                isPlaying = true
            ),
            onAction = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MusicDetailContentPausedPreview() {
    DelightMusicTheme {
        MusicDetailContent(
            uiState = MusicDetailUiState(
                title = "일시정지된 노래",
                artist = "아티스트",
                album = "",
                currentPosition = 30000,
                duration = 240000,
                isPlaying = false
            ),
            onAction = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MusicDetailContentEmptyPreview() {
    DelightMusicTheme {
        MusicDetailContent(
            uiState = MusicDetailUiState(),
            onAction = {}
        )
    }
}
