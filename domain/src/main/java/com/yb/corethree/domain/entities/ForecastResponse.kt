package com.yb.corethree.domain.entities

interface ForecastResponse {
    val forecasts: List<Forecast>
}

interface Forecast {
    val time: String
    val main: MainForecast?
    val weather: List<ForecastCondition>?
    val wind: ForecastWind?
}

interface MainForecast {
    val temp: Float
}

interface ForecastCondition {
    val main: String
    val description: String?
    val icon: String?
}

interface ForecastWind {
    val speed: Float
}