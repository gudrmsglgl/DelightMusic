package io.delight.datasource_musiclist.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.delight.datasource_musiclist.MusicListDataSourceImpl
import io.delight.datasource_musiclist_api.MusicListDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MusicListModule {
    @Binds
    @Singleton
    abstract fun bindMusicListRepository(
        musicListRepositoryImpl: MusicListDataSourceImpl
    ): MusicListDataSource
}