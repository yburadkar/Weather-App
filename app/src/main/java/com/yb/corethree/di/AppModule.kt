package com.yb.corethree.di

import com.google.gson.Gson
import com.yb.corethree.App
import com.yb.corethree.R
import com.yb.corethree.common.Navigator
import com.yb.corethree.common.ToolbarManager
import com.yb.corethree.data.entities.api.ApiCityList
import com.yb.corethree.domain.entities.CityList
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
class AppModule(private val application: App) {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideCityList(): CityList {
        return gson().fromJson(application.applicationContext.resources.openRawResource(R.raw.citylist).bufferedReader().readText(), ApiCityList::class.java)
    }

    @Singleton
    @Provides
    @Named("io")
    fun io() = Schedulers.io()

    @Singleton
    @Provides
    @Named("ui")
    fun ui() = AndroidSchedulers.mainThread()

    @Singleton
    @Provides
    fun provideNavigator() = Navigator

    @Singleton
    @Provides
    fun provideToolbarManager() = ToolbarManager

    @Singleton
    @Provides
    fun gson() = Gson()

    companion object {
        private const val BASE_URL = ""
    }

}