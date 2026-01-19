package io.delight.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.RepeatOne
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.delight.player_api.RepeatMode

@Composable
internal fun MusicController(
    isPlaying: Boolean,
    isBuffering: Boolean,
    shuffleEnabled: Boolean,
    repeatMode: RepeatMode,
    onPlayPauseClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onShuffleClick: () -> Unit,
    onRepeatClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onShuffleClick,
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Shuffle,
                contentDescription = "셔플",
                modifier = Modifier.size(28.dp),
                tint = if (shuffleEnabled) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                }
            )
        }

        IconButton(
            onClick = onPreviousClick,
            modifier = Modifier.size(56.dp)
        ) {
            Icon(
                imageVector = Icons.Default.SkipPrevious,
                contentDescription = "이전 곡",
                modifier = Modifier.size(36.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        Surface(
            onClick = onPlayPauseClick,
            modifier = Modifier.size(72.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primary
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                if (isBuffering) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(32.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 3.dp
                    )
                } else {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (isPlaying) "일시정지" else "재생",
                        modifier = Modifier.size(40.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }

        IconButton(
            onClick = onNextClick,
            modifier = Modifier.size(56.dp)
        ) {
            Icon(
                imageVector = Icons.Default.SkipNext,
                contentDescription = "다음 곡",
                modifier = Modifier.size(36.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        IconButton(
            onClick = onRepeatClick,
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                imageVector = when (repeatMode) {
                    RepeatMode.ONE -> Icons.Default.RepeatOne
                    else -> Icons.Default.Repeat
                },
                contentDescription = when (repeatMode) {
                    RepeatMode.OFF -> "반복 끔"
                    RepeatMode.ONE -> "한곡 반복"
                    RepeatMode.ALL -> "전체 반복"
                },
                modifier = Modifier.size(28.dp),
                tint = if (repeatMode != RepeatMode.OFF) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MusicControllerPlayingPreview() {
    MaterialTheme {
        MusicController(
            isPlaying = true,
            isBuffering = false,
            shuffleEnabled = false,
            repeatMode = RepeatMode.OFF,
            onPlayPauseClick = {},
            onPreviousClick = {},
            onNextClick = {},
            onShuffleClick = {},
            onRepeatClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MusicControllerShuffleAndRepeatPreview() {
    MaterialTheme {
        MusicController(
            isPlaying = true,
            isBuffering = false,
            shuffleEnabled = true,
            repeatMode = RepeatMode.ALL,
            onPlayPauseClick = {},
            onPreviousClick = {},
            onNextClick = {},
            onShuffleClick = {},
            onRepeatClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MusicControllerRepeatOnePreview() {
    MaterialTheme {
        MusicController(
            isPlaying = false,
            isBuffering = false,
            shuffleEnabled = false,
            repeatMode = RepeatMode.ONE,
            onPlayPauseClick = {},
            onPreviousClick = {},
            onNextClick = {},
            onShuffleClick = {},
            onRepeatClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MusicControllerBufferingPreview() {
    MaterialTheme {
        MusicController(
            isPlaying = false,
            isBuffering = true,
            shuffleEnabled = false,
            repeatMode = RepeatMode.OFF,
            onPlayPauseClick = {},
            onPreviousClick = {},
            onNextClick = {},
            onShuffleClick = {},
            onRepeatClick = {}
        )
    }
}