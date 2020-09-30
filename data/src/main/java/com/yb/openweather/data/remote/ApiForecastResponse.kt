package com.yb.openweather.data.remote

import com.google.gson.annotations.SerializedName
import com.yb.openweather.domain.entities.Forecast
import com.yb.openweather.domain.entities.ForecastCondition
import com.yb.openweather.domain.entities.ForecastResponse
import com.yb.openweather.domain.entities.ForecastWind
import com.yb.openweather.domain.entities.MainForecast

class ApiForecastResponse(
    @SerializedName("list")
    override val forecasts: List<ApiForecast>
) : ForecastResponse

class ApiForecast(
    @SerializedName("dt_txt")
    override val time: String,
    override val main: ApiMainForecast? = null,
    override val weather: List<ApiForecastCondition>? = null,
    override val wind: ApiForecastWind? = null
    ) : Forecast

class ApiMainForecast(
    override val temp: Float
) : MainForecast

class ApiForecastCondition(
    override val main: String,
    override val description: String? = null,
    override val icon: String? = null
) : ForecastCondition

class ApiForecastWind(
    override val speed: Float
) : ForecastWind
