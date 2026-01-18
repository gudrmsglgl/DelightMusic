package io.delight.domain_musiclist_api

import io.delight.model.Music
import kotlinx.coroutines.flow.Flow

interface GetMusicListUseCase {
    suspend operator fun invoke(): Flow<List<Music>>
}