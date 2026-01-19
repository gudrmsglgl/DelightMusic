package io.delight.delightmusic.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import io.delight.navigation.MusicDetailRoute
import io.delight.navigation.MusicListRoute
import io.delight.navigation.musicDetailScreen
import io.delight.navigation.musicListScreen

@Composable
fun DelightNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = MusicListRoute,
        modifier = modifier
    ) {
        musicListScreen(
            onMusicItemClick = {
                navController.navigate(MusicDetailRoute)
            }
        )
        musicDetailScreen(
            onBackClick = navController::popBackStack
        )
    }
}

