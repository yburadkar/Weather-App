package com.yb.corethree.data.remote

import com.yb.corethree.domain.entities.City
import com.yb.corethree.domain.entities.CityList


class ApiCityList(
    override val cities: List<ApiCity>
) : CityList

class ApiCity (
    override val id: Int? = null,
    override val name: String? = null,
    override val country: String? = null
) : City