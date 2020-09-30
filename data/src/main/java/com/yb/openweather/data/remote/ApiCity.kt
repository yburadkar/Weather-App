package com.yb.openweather.data.remote

import com.yb.openweather.domain.entities.City
import com.yb.openweather.domain.entities.CityList


class ApiCityList(
    override val cities: List<ApiCity>
) : CityList

class ApiCity (
    override val id: Int,
    override val name: String,
    override val country: String? = null
) : City