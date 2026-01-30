package io.delight.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.delight.musicdetail.MusicDetailScreen
import io.delight.musicdetail_api.MusicDetailRoute

fun NavGraphBuilder.musicDetailScreen() {
    composable<MusicDetailRoute> {
        MusicDetailScreen()
    }
}