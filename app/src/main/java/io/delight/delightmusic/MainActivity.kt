package io.delight.delightmusic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.delight.delightmusic.navigation.DelightNavHost
import io.delight.designsystem.theme.DelightMusicTheme
import io.delight.router.LaunchedRouter

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            LaunchedRouter(
                navHostController = rememberNavController()
            )

            DelightMusicTheme {
                DelightNavHost()
            }
        }
    }
}
