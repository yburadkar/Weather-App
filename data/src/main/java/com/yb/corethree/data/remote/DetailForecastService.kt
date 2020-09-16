package com.yb.corethree.data.remote

import com.yb.corethree.data.entities.api.ApiForecastResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface DetailForecastService {

    @GET("/forecast")
    fun getDetailWeather(@Query("id") cityId: Int): Single<ApiForecastResponse>

}