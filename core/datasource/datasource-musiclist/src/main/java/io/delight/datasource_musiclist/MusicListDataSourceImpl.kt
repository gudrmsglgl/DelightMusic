package io.delight.datasource_musiclist

import io.delight.datasource_musiclist.source.LocalMusicListDataSource
import io.delight.datasource_musiclist_api.MusicListDataSource
import io.delight.model.Music
import javax.inject.Inject

class MusicListDataSourceImpl
@Inject
constructor(
    private val localMusicListDataSource: LocalMusicListDataSource
): MusicListDataSource {
    override suspend fun getAllMusic(): List<Music> {
        return localMusicListDataSource.getAllMusic()
    }
}