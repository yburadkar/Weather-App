package com.yb.corethree.features.weather.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yb.corethree.BuildConfig
import com.yb.corethree.common.DetailWeatherNavigationEvent
import com.yb.corethree.common.DisposingViewModel
import com.yb.corethree.common.Navigator
import com.yb.corethree.common.Resource
import com.yb.corethree.domain.entities.CityList
import com.yb.corethree.domain.entities.CityWeatherResponse
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

    private val _cities = MutableLiveData<Resource<List<CityWeatherResponse>>>()
    val cities: LiveData<Resource<List<CityWeatherResponse>>> = _cities

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
            .switchMapSingle {
                _cities.postValue(Resource.loading(null))
                currentWeatherRepo.getCurrentWeather(it, BuildConfig.OPEN_WEATHER_API_KEY)
            }
            .subscribeOn(io)
            .observeOn(ui)
            .subscribeBy(
                onError = {
                    _cities.value = Resource.error(data = null, error = it)
                },
                onNext = {
                    _cities.value = Resource.success(data = it.cities)
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
