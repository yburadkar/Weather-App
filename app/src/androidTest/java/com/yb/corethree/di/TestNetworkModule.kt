package com.yb.corethree.di

import com.yb.corethree.data.remote.CurrentWeatherService
import com.yb.corethree.data.remote.DetailForecastService
import com.yb.corethree.data.remote.repos.CurrentWeatherRepository
import com.yb.corethree.data.remote.repos.DetailForecastRepository
import com.yb.corethree.data.remote.repos.FakeWeatherRepo
import com.yb.corethree.domain.repos.ICurrentWeatherRepository
import com.yb.corethree.domain.repos.IDetailForecastRepository
import dagger.Module
import dagger.Provides
import io.mockk.mockk
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
class TestNetworkModule(
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
    fun io() = Schedulers.trampoline()

    @Singleton
    @Provides
    @Named("ui")
    fun ui() = Schedulers.trampoline()

}