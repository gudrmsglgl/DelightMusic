package io.delight.datasource_musiclist_api

import io.delight.model.Music
import kotlinx.coroutines.flow.Flow

interface MusicListDataSource {
    suspend fun getAllMusic(): Flow<List<Music>>
}