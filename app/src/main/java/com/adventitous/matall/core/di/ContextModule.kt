package com.adventitous.matall.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
object ContextModule {
    @Provides
    fun provideContext(@ActivityContext context: Context): Context = context
}
