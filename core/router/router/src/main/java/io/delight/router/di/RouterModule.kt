package io.delight.router.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.delight.router.internal.navigator.InternalNavigator
import io.delight.router.internal.navigator.NavigatorImpl
import io.delight.router_api.Navigator

@Module
@InstallIn(ActivityRetainedComponent::class)
internal abstract class RouterModule {

    @Binds
    @ActivityRetainedScoped
    abstract fun bindsNavigator(
        navigator: NavigatorImpl
    ): Navigator

    @Binds
    @ActivityRetainedScoped
    abstract fun bindsInternalNavigator(
        navigator: NavigatorImpl
    ): InternalNavigator
}