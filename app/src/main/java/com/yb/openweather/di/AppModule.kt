package com.yb.openweather.di

import com.google.gson.Gson
import com.yb.openweather.App
import com.yb.openweather.R
import com.yb.openweather.common.Navigator
import com.yb.openweather.common.SimpleIdlingResource
import com.yb.openweather.common.ToolbarManager
import com.yb.openweather.data.remote.ApiCityList
import com.yb.openweather.domain.entities.CityList
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(private val application: App) {

    @Singleton
    @Provides
    fun provideCityList(): CityList {
        return gson().fromJson(application.applicationContext.resources.openRawResource(R.raw.citylist_gb_in_au).bufferedReader().readText(), ApiCityList::class.java)
    }

    @Singleton
    @Provides
    fun provideNavigator() = Navigator

    @Singleton
    @Provides
    fun provideToolbarManager() = ToolbarManager

    @Singleton
    @Provides
    fun gson() = Gson()

    @Singleton
    @Provides
    fun idlingRes() = SimpleIdlingResource

}