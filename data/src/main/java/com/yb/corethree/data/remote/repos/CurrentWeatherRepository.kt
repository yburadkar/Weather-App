package com.yb.corethree.data.remote.repos

import com.yb.corethree.data.remote.CurrentWeatherService
import com.yb.corethree.domain.entities.GroupWeatherResponse
import com.yb.corethree.domain.repos.ICurrentWeatherRepository
import io.reactivex.Single
import javax.inject.Inject

class CurrentWeatherRepository @Inject constructor(
    private val currentWeatherService: CurrentWeatherService
) : ICurrentWeatherRepository {

    override fun getCurrentWeather(group: List<Int>, key: String): Single<GroupWeatherResponse> {
        return currentWeatherService.getCurrentWeatherForCities(group.toStringList(), key).map { it as GroupWeatherResponse }
    }

    private fun List<Int>.toStringList() : String{
        val text = this.map{ it.toString() }.reduce { acc, i ->
            "$acc,$i"
        }
        return text
    }

}