package com.yb.corethree.domain.repos

import com.yb.corethree.domain.entities.ForecastResponse
import io.reactivex.Single

interface IDetailForecastRepository {
    fun getDetailForecast(id: Int, apiKey: String): Single<ForecastResponse>
}