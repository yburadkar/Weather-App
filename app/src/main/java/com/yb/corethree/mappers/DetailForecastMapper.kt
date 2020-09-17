package com.yb.corethree.mappers

import com.yb.corethree.domain.entities.Forecast
import com.yb.corethree.models.ForecastDate
import com.yb.corethree.models.ForecastEntry
import com.yb.corethree.models.ForecastItem
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
                    temp = it.main?.temp?.let { temp -> "${temp.roundToInt()} C" } ?: "NA",
                    description = it.weather?.get(0)?.main ?: "NA",
                    windSpeed = it.wind?.speed?.let { wind -> "${wind.roundToInt()} m/s" } ?: "NA"
                )
            )
        }
        return list
    }

}