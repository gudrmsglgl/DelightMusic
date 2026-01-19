package io.delight.delightmusic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import io.delight.delightmusic.navigation.DelightNavHost
import io.delight.designsystem.theme.DelightMusicTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        setContent {
            DelightMusicTheme {
                DelightNavHost()
            }
        }
    }
}
