package io.delight.router.internal.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.delight.router.internal.navigator.InternalNavigator
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class RouterViewModel @Inject constructor(
    navigator: InternalNavigator
) : ViewModel() {
    val sideEffect by lazy(LazyThreadSafetyMode.NONE) {
        navigator.channel.receiveAsFlow()
    }
}