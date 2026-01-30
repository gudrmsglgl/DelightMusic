package io.delight.router.internal.navigator

import kotlinx.coroutines.channels.Channel

interface InternalNavigator {
    val channel: Channel<InternalRoute>
}