package io.delight.router.internal.navigator

import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.delight.router_api.Navigator
import io.delight.router_api.model.Route
import kotlinx.coroutines.channels.Channel
import javax.inject.Inject

@ActivityRetainedScoped
internal class NavigatorImpl
@Inject constructor() : InternalNavigator, Navigator {

    override val channel: Channel<InternalRoute> = Channel(Channel.BUFFERED)

    override suspend fun navigate(
        route: Route,
        saveState: Boolean,
        launchSingleTop: Boolean
    ) {
        channel.send(
            InternalRoute.Navigate(
                route = route,
                saveState = saveState,
                launchSingleTop = launchSingleTop
            )
        )
    }

    override suspend fun navigateBack() {
        channel.send(InternalRoute.NavigateBack)
    }
}