package com.yb.corethree

import android.app.Application
import com.yb.corethree.di.AppComponent
import com.yb.corethree.di.AppModule
import com.yb.corethree.di.DaggerAppComponent
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