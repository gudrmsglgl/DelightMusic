package io.delight.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.delight.musiclist.MusicListScreen
import io.delight.musiclist_api.MusicListRoute

fun NavGraphBuilder.musicListScreen() {
    composable<MusicListRoute> {
        MusicListScreen()
    }
}