package com.yb.openweather.domain.repos

import com.yb.openweather.domain.entities.GroupWeatherResponse
import io.reactivex.Single

interface ICurrentWeatherRepository {
    fun getCurrentWeather(group: List<Int>, key: String): Single<GroupWeatherResponse>
}