package com.yb.openweather.data.remote.repos

import com.yb.openweather.data.remote.DetailForecastService
import com.yb.openweather.domain.repos.IDetailForecastRepository
import com.yb.openweather.domain.entities.ForecastResponse
import io.reactivex.Single
import javax.inject.Inject

class DetailForecastRepository @Inject constructor(
    private val forecastService: DetailForecastService
) : IDetailForecastRepository {

    override fun getDetailForecast(id: Int, apiKey: String): Single<ForecastResponse> {
        return forecastService.getDetailWeather(id, apiKey).map { it as ForecastResponse }
    }

}