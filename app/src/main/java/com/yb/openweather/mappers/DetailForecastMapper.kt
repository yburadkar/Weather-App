package com.yb.openweather.mappers

import com.yb.openweather.domain.entities.Forecast
import com.yb.openweather.models.ForecastDate
import com.yb.openweather.models.ForecastEntry
import com.yb.openweather.models.ForecastItem
import kotlin.math.roundToInt

object DetailForecastMapper {

    fun map(forecasts: List<Forecast>): List<ForecastItem> {
        val list = mutableListOf<ForecastItem>()
        var prevDate = ""
        forecasts.forEach {
            val timestamp = it.time.split(' ')
            val date = timestamp[0]
            val time = timestamp[1].dropLast(3)
            if(date != prevDate) {
                list.add(ForecastDate(date = date))
                prevDate = date
            }
            list.add(
                ForecastEntry(
                    time = time,
                    temp = it.main?.temp?.let { temp -> "${temp.roundToInt()}\u00B0C" } ?: "NA",
                    description = it.weather?.get(0)?.main ?: "NA",
                    windSpeed = it.wind?.speed?.let { wind -> "${wind.roundToInt()} m/s" } ?: "NA"
                )
            )
        }
        return list
    }

}