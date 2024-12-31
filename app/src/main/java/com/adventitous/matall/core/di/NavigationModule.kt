package com.adventitous.matall.core.di

import com.adventitous.matall.core.nav.NavigationImpl
import com.adventitous.matall.core.nav.NavigationInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class NavigationModule {

    @Binds
    @ActivityScoped
    abstract fun bindNavigation(navigationImpl: NavigationImpl): NavigationInterface
}
