package com.yb.corethree.domain.repos

import com.yb.corethree.domain.entities.GroupWeatherResponse
import io.reactivex.Single

interface ICurrentWeatherRepository {
    fun getCurrentWeather(group: List<Int>, key: String): Single<GroupWeatherResponse>
}