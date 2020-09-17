package com.yb.corethree.features.weather.search

import com.yb.corethree.BuildConfig
import com.yb.corethree.common.DetailWeatherNavigationEvent
import com.yb.corethree.common.DisposingViewModel
import com.yb.corethree.common.Navigator
import com.yb.corethree.domain.entities.CityList
import com.yb.corethree.domain.repos.ICurrentWeatherRepository
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

class SearchViewModel @Inject constructor(
    private val cityList: CityList,
    private val currentWeatherRepo: ICurrentWeatherRepository,
    @Named("io") private val io: Scheduler,
    @Named("ui") private val ui: Scheduler,
    private val navigator: Navigator
) : DisposingViewModel() {

    val searchText = PublishSubject.create<String>()

    init {
        getSearchResults()
    }

    private fun onSearchChange(): Observable<String> {
        return searchText
    }

    private fun getSearchResults() {
        onSearchChange().debounce(1000, TimeUnit.MILLISECONDS)
            .map { getMatchingCities(it) }
            .switchMapSingle { currentWeatherRepo.getCurrentWeather(it, BuildConfig.OPEN_WEATHER_API_KEY) }
            .subscribeOn(io)
            .observeOn(ui)
            .subscribeBy(
                onError = {
                    Timber.e(it)
                },
                onNext = {
                    Timber.d("Count = ${it.cities.size}")
                }
            ).addTo(disposables)
    }

    private fun getMatchingCities(searchText: String): List<Int> {
        return cityList.cities.filter {
            it.name.contains(other = searchText, ignoreCase = true)
        }.take(10).also { cities -> Timber.d("${cities.map { it.name }}") }
            .map { it.id }
    }

    fun navigateToDetail(cityId: Int) {
        navigator.sendNavigationEvent(DetailWeatherNavigationEvent(cityId))
    }

}