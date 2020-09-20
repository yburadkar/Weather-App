package com.yb.corethree.di

import com.google.gson.Gson
import com.yb.corethree.App
import com.yb.corethree.R
import com.yb.corethree.common.Navigator
import com.yb.corethree.common.SimpleIdlingResource
import com.yb.corethree.common.ToolbarManager
import com.yb.corethree.data.remote.ApiCityList
import com.yb.corethree.domain.entities.CityList
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