package com.yb.corethree.di

import com.google.gson.Gson
import com.yb.corethree.App
import com.yb.corethree.R
import com.yb.corethree.data.remote.ApiCityList
import com.yb.corethree.data.remote.ApiGroupWeatherResponse
import com.yb.corethree.data.remote.CurrentWeatherService
import com.yb.corethree.data.remote.DetailForecastService
import com.yb.corethree.domain.entities.CityList
import com.yb.corethree.domain.entities.GroupWeatherResponse
import com.yb.corethree.domain.repos.ICurrentWeatherRepository
import com.yb.corethree.domain.repos.IDetailForecastRepository
import com.yb.corethree.repos.FakeWeatherRepo
import dagger.Module
import dagger.Provides
import io.mockk.mockk
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
class TestNetworkModule(
    private val application: App,
    private val retrofit: Retrofit = mockk(),
    private val weatherService: CurrentWeatherService = mockk(),
    private val forecastService: DetailForecastService = mockk(),
    private val weatherRepository: ICurrentWeatherRepository = FakeWeatherRepo(),
    private val forecastRepository: IDetailForecastRepository = mockk()
) {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = mockk()

    @Singleton
    @Provides
    fun provideGroupWeatherService(retrofit: Retrofit): CurrentWeatherService = mockk()

    @Singleton
    @Provides
    fun provideDetailForecastService(retrofit: Retrofit): DetailForecastService = mockk()

    @Singleton
    @Provides
    fun provideCurrentWeatherRepository(): ICurrentWeatherRepository = weatherRepository

    @Singleton
    @Provides
    fun provideForecastRepository(): IDetailForecastRepository = forecastRepository

    @Singleton
    @Provides
    @Named("io")
    fun io() = Schedulers.trampoline()

    @Singleton
    @Provides
    @Named("ui")
    fun ui() = Schedulers.trampoline()

    @Singleton
    @Provides
    fun provideCityList(): CityList {
        return gson().fromJson(application.applicationContext.resources.openRawResource(R.raw.citylist_lon).bufferedReader().readText(), ApiCityList::class.java)
    }

    @Singleton
    @Provides
    fun provideWeatherList(): GroupWeatherResponse {
        return gson().fromJson(application.applicationContext.resources.openRawResource(R.raw.weatherlist_lon_api).bufferedReader().readText(), ApiGroupWeatherResponse::class.java)
    }

    @Singleton
    @Provides
    fun gson() = Gson()

}