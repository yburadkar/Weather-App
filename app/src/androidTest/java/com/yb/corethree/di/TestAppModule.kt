package com.yb.corethree.di

import com.yb.corethree.common.Navigator
import com.yb.corethree.common.SimpleIdlingResource
import com.yb.corethree.common.ToolbarManager
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