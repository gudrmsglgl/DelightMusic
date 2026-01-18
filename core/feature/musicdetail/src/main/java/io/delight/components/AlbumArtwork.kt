package io.delight.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
internal fun AlbumArtwork(
    albumArtUri: String?,
    modifier: Modifier = Modifier
) {
    val placeholderPainter = rememberVectorPainter(Icons.Default.Audiotrack)

    Surface(
        modifier = modifier
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        AsyncImage(
            model = albumArtUri,
            contentDescription = "앨범 아트",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            placeholder = placeholderPainter,
            error = placeholderPainter,
            fallback = placeholderPainter
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AlbumArtworkWithUriPreview() {
    MaterialTheme {
        AlbumArtwork(
            albumArtUri = "https://example.com/album.jpg",
            modifier = Modifier.size(300.dp)
        )
    }
}