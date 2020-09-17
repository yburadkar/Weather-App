package com.yb.corethree.data.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrentWeatherService {

    @GET("group")
    fun getCurrentWeatherForCities(
        @Query("id") group: String,
        @Query("APPID") apiKey: String,
        @Query("units") units: String = "metric"
    ): Single<ApiGroupWeatherResponse>

}