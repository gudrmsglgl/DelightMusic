package io.delight.domain_musiclist.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.delight.domain_musiclist.GetMusicListUseCaseImpl
import io.delight.domain_musiclist_api.GetMusicListUseCase

@InstallIn(SingletonComponent::class)
@Module
abstract class MusicListDomainModule {
    @Binds
    abstract fun bindGetMusicListUseCase(
        getMusicListUseCaseImpl: GetMusicListUseCaseImpl
    ): GetMusicListUseCase
}