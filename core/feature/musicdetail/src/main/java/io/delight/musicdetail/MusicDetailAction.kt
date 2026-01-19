package io.delight.musicdetail

sealed interface MusicDetailAction {
    data object PlayPause : MusicDetailAction
    data object Previous : MusicDetailAction
    data object Next : MusicDetailAction
    data class SeekTo(val progress: Float) : MusicDetailAction
    data object ToggleShuffle : MusicDetailAction
    data object CycleRepeatMode : MusicDetailAction
}
