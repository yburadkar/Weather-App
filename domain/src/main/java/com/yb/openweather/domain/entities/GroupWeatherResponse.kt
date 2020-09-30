package com.yb.openweather.domain.entities

interface GroupWeatherResponse {
    val cities: List<CityWeatherResponse>
}

interface CityWeatherResponse {
    val id: Int
    val name: String
    val sys: Country
    val time: Long
    val weather: List<WeatherCondition>?
    val main: MainWeather?
    val wind: Wind?
}

interface Country {
    val country: String
}

interface WeatherCondition {
    val main: String
    val description: String?
    val icon: String?
}

interface MainWeather {
    val temp: Float
}

interface Wind {
    val speed: Float
    val deg: Float
}
