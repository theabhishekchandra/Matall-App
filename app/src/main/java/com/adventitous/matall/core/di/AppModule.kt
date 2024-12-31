package com.adventitous.matall.core.di

import com.adventitous.matall.core.shareprefrence.AppSharedPref
import com.adventitous.matall.core.shareprefrence.AppSharedPrefInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindAppSharedPref(appSharedPref: AppSharedPref): AppSharedPrefInterface
}
