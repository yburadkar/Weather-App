package com.yb.corethree.domain.entities

interface CityList {
    val cities: List<City>
}

interface City {
    val id: Int
    val name: String
    val country: String?
}
