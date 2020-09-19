package com.yb.corethree.data.remote.repos

import com.yb.corethree.data.remote.ApiGroupWeatherResponse
import com.yb.corethree.domain.entities.GroupWeatherResponse
import com.yb.corethree.domain.repos.ICurrentWeatherRepository
import io.reactivex.Single

class FakeWeatherRepo: ICurrentWeatherRepository {



    override fun getCurrentWeather(group: List<Int>, key: String): Single<GroupWeatherResponse> {
        return if(group.isEmpty()) Single.just(ApiGroupWeatherResponse(cities = emptyList()))
        else {
            Single.just(ApiGroupWeatherResponse(cities = emptyList()))
        }
    }

}