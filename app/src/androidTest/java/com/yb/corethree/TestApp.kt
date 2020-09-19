package com.yb.corethree

import com.yb.corethree.di.AppComponent
import com.yb.corethree.di.AppModule
import com.yb.corethree.di.DaggerTestAppComponent

class TestApp: App() {

    override lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerTestAppComponent.builder().appModule(AppModule(this)).build()
    }

}