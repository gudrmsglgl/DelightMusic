package io.delight.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun MusicInfo(
    title: String,
    artist: String,
    album: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title.ifEmpty { "재생 중인 곡 없음" },
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = artist.ifEmpty { "-" },
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )

        if (album.isNotEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = album,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MusicInfoFullPreview() {
    MaterialTheme {
        MusicInfo(
            title = "Bohemian Rhapsody",
            artist = "Queen",
            album = "A Night at the Opera"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MusicInfoNoAlbumPreview() {
    MaterialTheme {
        MusicInfo(
            title = "Shape of You",
            artist = "Ed Sheeran",
            album = ""
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MusicInfoEmptyPreview() {
    MaterialTheme {
        MusicInfo(
            title = "",
            artist = "",
            album = ""
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MusicInfoLongTitlePreview() {
    MaterialTheme {
        MusicInfo(
            title = "This Is A Very Long Song Title That Should Be Truncated With Ellipsis",
            artist = "Artist With A Very Long Name That Should Also Be Truncated",
            album = "Album With An Extremely Long Name"
        )
    }
}