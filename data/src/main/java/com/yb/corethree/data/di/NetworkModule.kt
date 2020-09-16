package com.yb.corethree.data.di

import com.yb.corethree.data.remote.CurrentWeatherService
import com.yb.corethree.data.remote.DetailForecastService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideGroupWeatherService(retrofit: Retrofit): CurrentWeatherService {
        return retrofit.create(CurrentWeatherService::class.java)
    }

    @Singleton
    @Provides
    fun provideDetailForecastService(retrofit: Retrofit): DetailForecastService {
        return retrofit.create(DetailForecastService::class.java)
    }

    companion object {
        private const val BASE_URL = "http://api.openweathermap.org/data/2.5"
    }

}