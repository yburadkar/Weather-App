package com.yb.corethree.repos

import com.yb.corethree.data.remote.ApiCityWeatherResponse
import com.yb.corethree.data.remote.ApiGroupWeatherResponse
import com.yb.corethree.domain.entities.GroupWeatherResponse
import com.yb.corethree.domain.repos.ICurrentWeatherRepository
import io.reactivex.Single
import javax.inject.Inject

class FakeWeatherRepo : ICurrentWeatherRepository {

    @Inject lateinit var weatherList: GroupWeatherResponse

    override fun getCurrentWeather(group: List<Int>, key: String): Single<GroupWeatherResponse> {
        return if (group.isEmpty()) Single.just(getWeatherForGroup(group))
        else {
            Single.just(ApiGroupWeatherResponse(cities = emptyList()))
        }
    }

    private fun getWeatherForGroup(group: List<Int>): ApiGroupWeatherResponse {
        val list = weatherList.cities.filter { group.contains(it.id) }.map { it as ApiCityWeatherResponse }
        return ApiGroupWeatherResponse(cities = list)
    }

}