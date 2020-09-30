package com.yb.openweather.data.remote.repos

import com.yb.openweather.data.remote.ApiGroupWeatherResponse
import com.yb.openweather.data.remote.CurrentWeatherService
import com.yb.openweather.domain.entities.GroupWeatherResponse
import com.yb.openweather.domain.repos.ICurrentWeatherRepository
import io.reactivex.Single
import javax.inject.Inject

class CurrentWeatherRepository @Inject constructor(
    private val currentWeatherService: CurrentWeatherService
) : ICurrentWeatherRepository {

    override fun getCurrentWeather(group: List<Int>, key: String): Single<GroupWeatherResponse> {
        return if(group.isEmpty()) Single.just(ApiGroupWeatherResponse(cities = emptyList()))
        else currentWeatherService.getCurrentWeatherForCities(group.toStringList(), key).map { it as GroupWeatherResponse }
    }

    private fun List<Int>.toStringList() : String{
        val text = this.map{ it.toString() }.reduce { acc, i ->
            "$acc,$i"
        }
        return text
    }

}