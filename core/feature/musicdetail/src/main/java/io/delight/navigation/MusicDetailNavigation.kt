package io.delight.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.delight.musicdetail.MusicDetailScreen

fun NavGraphBuilder.musicDetailScreen(
    onBackClick: () -> Unit
) {
    composable<MusicDetailRoute> {
        MusicDetailScreen(
            onBackClick = onBackClick
        )
    }
}