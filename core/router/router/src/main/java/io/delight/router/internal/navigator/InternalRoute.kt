package io.delight.router.internal.navigator
import io.delight.router_api.model.Route

sealed interface InternalRoute {
    data object NavigateBack : InternalRoute

    data class Navigate(
        val route: Route,
        val saveState: Boolean,
        val launchSingleTop: Boolean
    ) : InternalRoute
}
