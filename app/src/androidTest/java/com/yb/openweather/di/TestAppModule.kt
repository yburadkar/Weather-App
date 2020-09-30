package com.yb.openweather.di

import com.yb.openweather.common.Navigator
import com.yb.openweather.common.SimpleIdlingResource
import com.yb.openweather.common.ToolbarManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class TestAppModule {

    @Singleton
    @Provides
    fun provideNavigator() = Navigator

    @Singleton
    @Provides
    fun provideToolbarManager() = ToolbarManager

    @Singleton
    @Provides
    fun idlingRes() = SimpleIdlingResource

}