package com.yb.corethree.models

import java.util.*


sealed class ForecastItem(val id: String = UUID.randomUUID().toString())

data class ForecastDate(
    private val date: String
): ForecastItem()

data class ForecastEntry(
    private val time: String,
    private val temp: String,
    private val description: String,
    private val windSpeed: String
): ForecastItem()