package com.yb.corethree.models

import java.util.*

sealed class ForecastItem(val type: Int, val id: String = UUID.randomUUID().toString()) {
    companion object {
        const val FORECAST_DATE = 0
        const val FORECAST_ENTRY = 1
    }
}

data class ForecastDate(
    val date: String
) : ForecastItem(FORECAST_DATE)

data class ForecastEntry(
    val time: String,
    val temp: String,
    val description: String,
    val windSpeed: String
) : ForecastItem(FORECAST_ENTRY)