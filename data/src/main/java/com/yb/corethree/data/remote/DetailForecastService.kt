package com.yb.corethree.data.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface DetailForecastService {

    @GET("forecast")
    fun getDetailWeather(
        @Query("id") cityId: Int,
        @Query("APPID") apiKey: String,
        @Query("units") units: String = "metric"
    ): Single<ApiForecastResponse>

}