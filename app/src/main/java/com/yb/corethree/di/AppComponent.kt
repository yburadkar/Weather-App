package com.yb.corethree.di

import com.yb.corethree.data.di.NetworkModule
import com.yb.corethree.features.main.MainActivity
import com.yb.corethree.features.weather.search.SearchFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class, NetworkModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)
    fun inject(fragment: SearchFragment)

}