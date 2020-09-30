package com.yb.openweather.di

import com.yb.openweather.data.di.NetworkModule
import com.yb.openweather.features.main.MainActivity
import com.yb.openweather.features.weather.detail.DetailForecastFragment
import com.yb.openweather.features.weather.search.SearchFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class, NetworkModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)
    fun inject(fragment: SearchFragment)
    fun inject(fragment: DetailForecastFragment)

}