package io.delight.router_api

import io.delight.router_api.model.Route

interface Navigator {
    suspend fun navigate(
        route: Route,
        saveState: Boolean = false,
        launchSingleTop: Boolean = false
    )

    suspend fun navigateBack()
}