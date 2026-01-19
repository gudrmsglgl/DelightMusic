package io.delight.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.delight.musiclist.MusicListScreen

fun NavGraphBuilder.musicListScreen(
    onMusicItemClick: () -> Unit
) {
    composable<MusicListRoute> {
        MusicListScreen(
            onMusicItemClick = onMusicItemClick
        )
    }
}