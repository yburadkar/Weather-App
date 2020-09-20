package com.yb.corethree.repos

import com.google.gson.Gson
import com.yb.corethree.data.remote.ApiCityWeatherResponse
import com.yb.corethree.data.remote.ApiGroupWeatherResponse
import com.yb.corethree.domain.entities.GroupWeatherResponse
import com.yb.corethree.domain.repos.ICurrentWeatherRepository
import io.reactivex.Single

class FakeWeatherRepo : ICurrentWeatherRepository {

    private val weather =
        "{\"list\":[{\"id\":1256523,\"main\":{\"temp\":33.0},\"name\":\"Shillong\",\"sys\":{\"country\":\"IN\"},\"dt\":1600592990,\"weather\":[{\"description\":\"scattered clouds\",\"icon\":\"03d\",\"main\":\"Clouds\"}],\"wind\":{\"deg\":160.0,\"speed\":2.1}},{\"id\":1257599,\"main\":{\"temp\":35.07},\"name\":\"Salon\",\"sys\":{\"country\":\"IN\"},\"dt\":1600592990,\"weather\":[{\"description\":\"clear sky\",\"icon\":\"01d\",\"main\":\"Clear\"}],\"wind\":{\"deg\":54.0,\"speed\":2.53}},{\"id\":1260667,\"main\":{\"temp\":29.11},\"name\":\"Pāloncha\",\"sys\":{\"country\":\"IN\"},\"dt\":1600592991,\"weather\":[{\"description\":\"moderate rain\",\"icon\":\"10d\",\"main\":\"Rain\"}],\"wind\":{\"deg\":234.0,\"speed\":2.67}},{\"id\":1264773,\"main\":{\"temp\":35.51},\"name\":\"Loni\",\"sys\":{\"country\":\"IN\"},\"dt\":1600592992,\"weather\":[{\"description\":\"haze\",\"icon\":\"50d\",\"main\":\"Haze\"}],\"wind\":{\"deg\":270.0,\"speed\":2.1}},{\"id\":1264792,\"main\":{\"temp\":24.92},\"name\":\"Londa\",\"sys\":{\"country\":\"IN\"},\"dt\":1600592992,\"weather\":[{\"description\":\"broken clouds\",\"icon\":\"04d\",\"main\":\"Clouds\"}],\"wind\":{\"deg\":0.0,\"speed\":1.0}},{\"id\":1264793,\"main\":{\"temp\":31.0},\"name\":\"Lonavla\",\"sys\":{\"country\":\"IN\"},\"dt\":1600592992,\"weather\":[{\"description\":\"haze\",\"icon\":\"50d\",\"main\":\"Haze\"}],\"wind\":{\"deg\":290.0,\"speed\":3.6}},{\"id\":1264794,\"main\":{\"temp\":30.17},\"name\":\"Lonār\",\"sys\":{\"country\":\"IN\"},\"dt\":1600592992,\"weather\":[{\"description\":\"light rain\",\"icon\":\"10d\",\"main\":\"Rain\"}],\"wind\":{\"deg\":19.0,\"speed\":4.03}},{\"id\":1268510,\"main\":{\"temp\":34.08},\"name\":\"Kālāgarh Project Colony\",\"sys\":{\"country\":\"IN\"},\"dt\":1600592993,\"weather\":[{\"description\":\"clear sky\",\"icon\":\"01d\",\"main\":\"Clear\"}],\"wind\":{\"deg\":265.0,\"speed\":2.75}},{\"id\":1270543,\"main\":{\"temp\":35.48},\"name\":\"Hāflong\",\"sys\":{\"country\":\"IN\"},\"dt\":1600592993,\"weather\":[{\"description\":\"broken clouds\",\"icon\":\"04d\",\"main\":\"Clouds\"}],\"wind\":{\"deg\":83.0,\"speed\":1.28}},{\"id\":1273850,\"main\":{\"temp\":35.35},\"name\":\"Colonelganj\",\"sys\":{\"country\":\"IN\"},\"dt\":1600592994,\"weather\":[{\"description\":\"clear sky\",\"icon\":\"01d\",\"main\":\"Clear\"}],\"wind\":{\"deg\":63.0,\"speed\":1.64}},{\"id\":1276502,\"main\":{\"temp\":33.06},\"name\":\"Belonia\",\"sys\":{\"country\":\"IN\"},\"dt\":1600592995,\"weather\":[{\"description\":\"moderate rain\",\"icon\":\"10d\",\"main\":\"Rain\"}],\"wind\":{\"deg\":197.0,\"speed\":1.07}},{\"id\":1278969,\"main\":{\"temp\":36.69},\"name\":\"Along\",\"sys\":{\"country\":\"IN\"},\"dt\":1600592995,\"weather\":[{\"description\":\"clear sky\",\"icon\":\"01d\",\"main\":\"Clear\"}],\"wind\":{\"deg\":157.0,\"speed\":0.97}},{\"id\":2065113,\"main\":{\"temp\":15.8},\"name\":\"Mypolonga Flat\",\"sys\":{\"country\":\"AU\"},\"dt\":1600593030,\"weather\":[{\"description\":\"clear sky\",\"icon\":\"01n\",\"main\":\"Clear\"}],\"wind\":{\"deg\":308.0,\"speed\":4.92}},{\"id\":2066585,\"main\":{\"temp\":14.29},\"name\":\"Meelon\",\"sys\":{\"country\":\"AU\"},\"dt\":1600593030,\"weather\":[{\"description\":\"broken clouds\",\"icon\":\"04d\",\"main\":\"Clouds\"}],\"wind\":{\"deg\":230.0,\"speed\":9.8}},{\"id\":2067496,\"main\":{\"temp\":16.47},\"name\":\"Long Plains\",\"sys\":{\"country\":\"AU\"},\"dt\":1600593030,\"weather\":[{\"description\":\"scattered clouds\",\"icon\":\"03n\",\"main\":\"Clouds\"}],\"wind\":{\"deg\":0.0,\"speed\":5.36}},{\"id\":2068169,\"main\":{\"temp\":16.1},\"name\":\"Kuitpo Colony\",\"sys\":{\"country\":\"AU\"},\"dt\":1600593030,\"weather\":[{\"description\":\"scattered clouds\",\"icon\":\"03n\",\"main\":\"Clouds\"}],\"wind\":{\"deg\":308.0,\"speed\":4.92}},{\"id\":2142493,\"main\":{\"temp\":17.1},\"name\":\"Yarramalong\",\"sys\":{\"country\":\"AU\"},\"dt\":1600593032,\"weather\":[{\"description\":\"light rain\",\"icon\":\"10n\",\"main\":\"Rain\"}],\"wind\":{\"deg\":12.0,\"speed\":1.96}},{\"id\":2142813,\"main\":{\"temp\":15.32},\"name\":\"Wyalong\",\"sys\":{\"country\":\"AU\"},\"dt\":1600593033,\"weather\":[{\"description\":\"clear sky\",\"icon\":\"01n\",\"main\":\"Clear\"}],\"wind\":{\"deg\":359.0,\"speed\":1.72}},{\"id\":2142886,\"main\":{\"temp\":20.0},\"name\":\"Worrolong\",\"sys\":{\"country\":\"AU\"},\"dt\":1600593033,\"weather\":[{\"description\":\"scattered clouds\",\"icon\":\"03n\",\"main\":\"Clouds\"}],\"wind\":{\"deg\":10.0,\"speed\":7.2}},{\"id\":2143244,\"main\":{\"temp\":20.19},\"name\":\"Wollongbar\",\"sys\":{\"country\":\"AU\"},\"dt\":1600593033,\"weather\":[{\"description\":\"clear sky\",\"icon\":\"01n\",\"main\":\"Clear\"}],\"wind\":{\"deg\":360.0,\"speed\":6.2}}]}"

    val weatherList: ApiGroupWeatherResponse = Gson().fromJson(weather, ApiGroupWeatherResponse::class.java)

    override fun getCurrentWeather(group: List<Int>, key: String): Single<GroupWeatherResponse> {
        return if (group.isNotEmpty()) Single.just(getWeatherForGroup(group))
        else {
            Single.just(ApiGroupWeatherResponse(cities = emptyList()))
        }
    }

    private fun getWeatherForGroup(group: List<Int>): ApiGroupWeatherResponse {
        val list = weatherList.cities.filter { group.contains(it.id) }.map { it as ApiCityWeatherResponse }
        return ApiGroupWeatherResponse(cities = list)
    }

}