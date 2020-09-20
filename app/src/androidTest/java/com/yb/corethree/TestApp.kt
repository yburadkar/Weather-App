package com.yb.corethree

import com.yb.corethree.di.AppComponent
import com.yb.corethree.di.DaggerTestAppComponent
import com.yb.corethree.di.TestNetworkModule

class TestApp : App() {

    override lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerTestAppComponent.builder().testNetworkModule(TestNetworkModule(this)).build()
    }

}