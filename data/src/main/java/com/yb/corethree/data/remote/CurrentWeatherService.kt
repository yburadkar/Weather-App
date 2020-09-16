package com.yb.corethree.data.remote

import com.yb.corethree.data.entities.api.ApiGroupWeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrentWeatherService {

    @GET("/group")
    fun getCurrentWeatherForCities(@Query("id") group: List<Int>, @Query("APPID") apiKey: String): Single<ApiGroupWeatherResponse>

}