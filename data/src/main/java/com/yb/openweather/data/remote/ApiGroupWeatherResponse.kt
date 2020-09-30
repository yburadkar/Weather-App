package com.yb.openweather.data.remote

import com.google.gson.annotations.SerializedName
import com.yb.openweather.domain.entities.CityWeatherResponse
import com.yb.openweather.domain.entities.Country
import com.yb.openweather.domain.entities.GroupWeatherResponse
import com.yb.openweather.domain.entities.MainWeather
import com.yb.openweather.domain.entities.WeatherCondition
import com.yb.openweather.domain.entities.Wind

class ApiGroupWeatherResponse(
    @SerializedName("list")
    override val cities: List<ApiCityWeatherResponse>
) : GroupWeatherResponse

class ApiCityWeatherResponse(
    override val id: Int,
    override val name: String,
    override val sys: ApiCountry,
    @SerializedName("dt")
    override val time: Long,
    override val weather: List<ApiWeatherCondition>? = null,
    override val main: ApiMainWeather? = null,
    override val wind: ApiWind? = null
) : CityWeatherResponse

class ApiCountry(override val country: String) : Country

class ApiWeatherCondition(
    override val main: String,
    override val description: String? = null,
    override val icon: String? = null
) : WeatherCondition

class ApiMainWeather(
    override val temp: Float
) : MainWeather

class ApiWind(
    override val speed: Float,
    override val deg: Float
) : Wind
