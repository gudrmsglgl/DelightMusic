package io.delight.player

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Player.PositionInfo
import io.delight.player_api.PlayerState
import io.delight.player_api.RepeatMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

interface MediaPlayerListener {
    fun playerStateFlow(): Flow<PlayerState>
}

class MediaPlayerListenerImpl @Inject constructor(
    private val connectedMediaController: ConnectedMediaController
) : MediaPlayerListener {

    override fun playerStateFlow(): Flow<PlayerState> = callbackFlow {
        val mediaController = connectedMediaController.mediaControllerFlow.first()

        val flowScope = CoroutineScope(Dispatchers.Main)
        var timerJob: Job? = null

        val currentPlayerState = MutableStateFlow(
            PlayerState(
                isPlaying = mediaController.isPlaying,
                currentMediaItem = mediaController.currentMediaItem,
                currentPosition = mediaController.currentPosition,
                duration = mediaController.duration.takeIf { it > 0 } ?: 0L,
                playbackState = mediaController.playbackState,
                isConnected = true,
                shuffleEnabled = mediaController.shuffleModeEnabled,
                repeatMode = RepeatMode.fromPlayer(mediaController.repeatMode)
            )
        )

        fun startPositionUpdateTimer() {
            timerJob?.cancel()
            timerJob = flowScope.launch {
                while (isActive) {
                    delay(POSITION_UPDATE_INTERVAL_MS)
                    currentPlayerState.update {
                        it.copy(
                            currentPosition = mediaController.currentPosition,
                            duration = mediaController.duration.takeIf { d -> d > 0 } ?: it.duration
                        )
                    }
                }
            }
        }

        fun stopPositionUpdateTimer() {
            timerJob?.cancel()
            timerJob = null
        }

        val playerListener = object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)
                currentPlayerState.update { it.copy(isPlaying = isPlaying) }
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                currentPlayerState.update {
                    it.copy(
                        playbackState = playbackState,
                        duration = mediaController.duration.takeIf { d -> d > 0 } ?: it.duration
                    )
                }
            }

            override fun onMediaItemTransition(
                item: MediaItem?,
                reason: Int
            ) {
                super.onMediaItemTransition(item, reason)
                currentPlayerState.update {
                    it.copy(
                        currentMediaItem = item,
                        duration = mediaController.duration.takeIf { d -> d > 0 } ?: it.duration
                    )
                }
            }

            override fun onPositionDiscontinuity(
                oldPosition: PositionInfo,
                newPosition: PositionInfo,
                reason: Int
            ) {
                super.onPositionDiscontinuity(oldPosition, newPosition, reason)
                if (reason == Player.DISCONTINUITY_REASON_SEEK) {
                    currentPlayerState.update {
                        it.copy(currentPosition = newPosition.positionMs)
                    }
                }
            }

            override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
                super.onShuffleModeEnabledChanged(shuffleModeEnabled)
                currentPlayerState.update {
                    it.copy(shuffleEnabled = shuffleModeEnabled)
                }
            }

            override fun onRepeatModeChanged(repeatMode: Int) {
                super.onRepeatModeChanged(repeatMode)
                currentPlayerState.update {
                    it.copy(repeatMode = RepeatMode.fromPlayer(repeatMode))
                }
            }
        }

        flowScope.launch {
            currentPlayerState
                .map { it.isBuffering.not() && it.isPlaying }
                .distinctUntilChanged()
                .collect { isPlaying ->
                    if (isPlaying) {
                        startPositionUpdateTimer()
                    } else {
                        stopPositionUpdateTimer()
                    }
                }
        }

        flowScope.launch {
            currentPlayerState
                .onEach { send(it) }
                .collect()
        }

        mediaController.addListener(playerListener)

        awaitClose {
            mediaController.removeListener(playerListener)
            timerJob?.cancel()
            flowScope.cancel()
        }
    }

    companion object {
        private const val POSITION_UPDATE_INTERVAL_MS = 500L
    }
}
