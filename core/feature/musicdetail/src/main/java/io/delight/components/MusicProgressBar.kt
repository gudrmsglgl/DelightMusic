package io.delight.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.Locale
import java.util.concurrent.TimeUnit

@Composable
internal fun MusicProgressBar(
    currentPosition: Long,
    duration: Long,
    progress: Float,
    onProgressChange: (Float) -> Unit
) {
    var isDragging by remember { mutableStateOf(false) }
    var dragProgress by remember { mutableFloatStateOf(0f) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Slider(
            value = if (isDragging) dragProgress else progress,
            onValueChange = { newValue ->
                isDragging = true
                dragProgress = newValue
            },
            onValueChangeFinished = {
                onProgressChange(dragProgress)
                isDragging = false
            },
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.primary,
                inactiveTrackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
            )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = formatDuration(currentPosition),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = formatDuration(duration),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun formatDuration(durationMs: Long): String {
    if (durationMs <= 0) return "0:00"

    val minutes = TimeUnit.MILLISECONDS.toMinutes(durationMs)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(durationMs) % 60

    return String.format(Locale.getDefault(), "%d:%02d", minutes, seconds)
}

@Preview(showBackground = true, name = "MusicProgressBar - Middle")
@Composable
private fun MusicProgressBarMiddlePreview() {
    MaterialTheme {
        MusicProgressBar(
            currentPosition = 90_000L,
            duration = 210_000L,
            progress = 0.43f,
            onProgressChange = {}
        )
    }
}

@Preview(showBackground = true, name = "MusicProgressBar - Start")
@Composable
private fun MusicProgressBarStartPreview() {
    MaterialTheme {
        MusicProgressBar(
            currentPosition = 0L,
            duration = 180_000L,
            progress = 0f,
            onProgressChange = {}
        )
    }
}

@Preview(showBackground = true, name = "MusicProgressBar - Near End")
@Composable
private fun MusicProgressBarNearEndPreview() {
    MaterialTheme {
        MusicProgressBar(
            currentPosition = 170_000L,
            duration = 180_000L,
            progress = 0.94f,
            onProgressChange = {}
        )
    }
}