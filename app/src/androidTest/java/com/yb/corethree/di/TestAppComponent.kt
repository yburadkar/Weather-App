package com.yb.corethree.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [TestAppModule::class, TestNetworkModule::class, ViewModelModule::class])
interface TestAppComponent: AppComponent {

}