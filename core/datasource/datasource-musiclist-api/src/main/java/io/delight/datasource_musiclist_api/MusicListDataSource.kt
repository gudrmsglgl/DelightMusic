package io.delight.datasource_musiclist_api

import io.delight.model.Music

interface MusicListDataSource {
    suspend fun getAllMusic(): List<Music>
}