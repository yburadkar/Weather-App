package com.yb.openweather

import com.yb.openweather.di.AppComponent
import com.yb.openweather.di.DaggerTestAppComponent
import com.yb.openweather.di.TestNetworkModule

class TestApp : App() {

    override lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerTestAppComponent.builder().testNetworkModule(TestNetworkModule(this)).build()
    }

}