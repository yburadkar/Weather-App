package com.yb.openweather

import android.app.Application
import com.yb.openweather.di.AppComponent
import com.yb.openweather.di.AppModule
import com.yb.openweather.di.DaggerAppComponent
import timber.log.Timber

open class App: Application() {

    open lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initDi()
        initTimber()
    }

    private fun initDi() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    private fun initTimber() {
        if(BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

}