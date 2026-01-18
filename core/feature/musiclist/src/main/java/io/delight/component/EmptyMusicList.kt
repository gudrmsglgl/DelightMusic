package io.delight.component

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.delight.core.feature.musiclist.R

@Composable
internal fun BoxScope.EmptyMusicList() {
    Text(
        text = stringResource(R.string.music_list_empty),
        modifier = Modifier.align(Alignment.Center)
    )
}

