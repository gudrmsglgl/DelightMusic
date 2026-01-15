package io.delight.model

data class Music(
    val id: Long,
    val title: String,
    val artist: String,
    val album: String,
    val albumId: Long,
    val durationMs: Long,
    val fileUrl: String,
    val imageUrl: String
)