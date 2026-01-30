package io.delight.router

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import io.delight.router.internal.navigator.InternalRoute
import io.delight.router.internal.viewmodel.RouterViewModel

@Composable
fun LaunchedRouter(
    navHostController: NavHostController
) {
    InternalLaunchedRouter(navHostController)
}

@Composable
private fun InternalLaunchedRouter(
    navHostController: NavHostController,
    routerViewModel: RouterViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(routerViewModel, lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            routerViewModel.sideEffect.collect { sideEffect ->
                when (sideEffect) {
                    is InternalRoute.NavigateBack -> {
                        navHostController.popBackStack()
                    }
                    is InternalRoute.Navigate -> {
                        navHostController.navigate(sideEffect.route) {
                            if (sideEffect.saveState) {
                                navHostController.graph.findStartDestination().route?.let {
                                    popUpTo(it) {
                                        saveState = true
                                    }
                                }
                                restoreState = true
                            }
                            launchSingleTop = sideEffect.launchSingleTop
                        }
                    }
                }
            }
        }
    }
}
