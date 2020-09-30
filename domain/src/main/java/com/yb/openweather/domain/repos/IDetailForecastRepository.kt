package com.yb.openweather.domain.repos

import com.yb.openweather.domain.entities.ForecastResponse
import io.reactivex.Single

interface IDetailForecastRepository {
    fun getDetailForecast(id: Int, apiKey: String): Single<ForecastResponse>
}