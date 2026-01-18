package io.delight.player.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.delight.player.MediaControllerManager
import io.delight.player_api.MusicPlayerManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PlayerModule {

    @Binds
    @Singleton
    abstract fun bindMusicPlayerManager(
        mediaControllerManager: MediaControllerManager
    ): MusicPlayerManager
}
