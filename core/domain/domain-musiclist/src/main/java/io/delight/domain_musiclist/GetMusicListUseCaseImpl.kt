package io.delight.domain_musiclist

import io.delight.datasource_musiclist_api.MusicListDataSource
import io.delight.domain_musiclist_api.GetMusicListUseCase
import io.delight.model.Music
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMusicListUseCaseImpl @Inject constructor(
    private val musicListDataSource: MusicListDataSource
) : GetMusicListUseCase {

    override suspend fun invoke(): Flow<List<Music>> {
        return musicListDataSource.getAllMusic()
    }
}
