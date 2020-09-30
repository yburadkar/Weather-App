package com.yb.openweather.mappers

import com.yb.openweather.domain.entities.CityWeatherResponse
import com.yb.openweather.models.City
import kotlin.math.roundToInt

object SearchCityMapper {
    fun map (cities: List<CityWeatherResponse>?): List<City> {
        return cities?.map { response ->
            City(
                id = response.id,
                name = "${response.name}, ${response.sys.country}",
                temp = response.main?.temp?.roundToInt()?.let { "$it\u00B0C" } ?: "NA",
                description = response.weather?.first()?.main ?: "",
                speed = response.wind?.speed?.roundToInt()?.let { "$it m/s" } ?: "",
                direction = response.wind?.deg ?: 0.0F
            )
        } ?: emptyList()
    }
}