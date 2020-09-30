package com.yb.openweather.data.di

import com.yb.openweather.data.remote.CurrentWeatherService
import com.yb.openweather.data.remote.DetailForecastService
import com.yb.openweather.data.remote.repos.CurrentWeatherRepository
import com.yb.openweather.data.remote.repos.DetailForecastRepository
import com.yb.openweather.domain.repos.ICurrentWeatherRepository
import com.yb.openweather.domain.repos.IDetailForecastRepository
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

    @Singleton
    @Provides
    fun provideCurrentWeatherRepository(currentWeatherRepository: CurrentWeatherRepository): ICurrentWeatherRepository = currentWeatherRepository

    @Singleton
    @Provides
    fun provideForecastRepository(forecastRepository: DetailForecastRepository): IDetailForecastRepository = forecastRepository

    @Singleton
    @Provides
    @Named("io")
    fun io() = Schedulers.io()

    @Singleton
    @Provides
    @Named("ui")
    fun ui() = AndroidSchedulers.mainThread()

    companion object {
        private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    }

}