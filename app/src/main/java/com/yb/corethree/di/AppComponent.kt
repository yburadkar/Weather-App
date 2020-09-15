package com.yb.corethree.di

import com.yb.corethree.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)

}