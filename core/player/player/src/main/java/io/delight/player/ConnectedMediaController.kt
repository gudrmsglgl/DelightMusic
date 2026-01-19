package io.delight.player

import androidx.media3.session.MediaController
import com.google.common.util.concurrent.FutureCallback
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.cancellation.CancellationException

interface ConnectedMediaController {
    val mediaControllerFlow: Flow<MediaController>
}

@Singleton
class ConnectedMediaControllerImpl
@Inject constructor(
    mediaControllerFuture: ListenableFuture<MediaController>
): ConnectedMediaController {
    override val mediaControllerFlow: Flow<MediaController> = callbackFlow {
        Futures.addCallback(
            mediaControllerFuture,
            object : FutureCallback<MediaController> {
                override fun onSuccess(result: MediaController) {
                    trySend(result)
                }

                override fun onFailure(t: Throwable) {
                    cancel(CancellationException(t.message))
                }
            },
            MoreExecutors.directExecutor()
        )

        awaitClose { }
    }
}