package com.yb.corethree.mappers

import com.yb.corethree.domain.entities.CityWeatherResponse
import com.yb.corethree.models.City
import kotlin.math.roundToInt

object SearchCityMapper {
    fun map (cities: List<CityWeatherResponse>?): List<City> {
        return cities?.map { response ->
            City(
                id = response.id,
                name = "${response.name}, ${response.sys.country}",
                temp = response.main?.temp?.roundToInt()?.let { "$it c" } ?: "NA",
                description = response.weather?.first()?.main ?: "",
                speed = response.wind?.speed?.roundToInt()?.let { "$it m/s" } ?: "",
                direction = response.wind?.deg ?: 0.0F
            )
        } ?: emptyList()
    }
}