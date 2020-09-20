package com.yb.corethree.features.weather.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.yb.corethree.BuildConfig
import com.yb.corethree.common.DetailWeatherNavigationEvent
import com.yb.corethree.common.DisposingViewModel
import com.yb.corethree.common.Navigator
import com.yb.corethree.common.Resource
import com.yb.corethree.common.SimpleIdlingResource
import com.yb.corethree.common.TextToolbarUpdate
import com.yb.corethree.common.ToolbarManager
import com.yb.corethree.data.remote.ApiCity
import com.yb.corethree.domain.entities.CityList
import com.yb.corethree.domain.entities.CityWeatherResponse
import com.yb.corethree.domain.repos.ICurrentWeatherRepository
import com.yb.corethree.models.City
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
    private val navigator: Navigator,
    private val toolbarManager: ToolbarManager,
    private val idlingRes: SimpleIdlingResource
) : DisposingViewModel() {

    private val _cities = MutableLiveData<Resource<List<CityWeatherResponse>>>()
    val cities: LiveData<Resource<List<CityWeatherResponse>>> = _cities

    val searchText = PublishSubject.create<String>()
    var prevSearchTerm = ""

    init {
        getSearchResults()
    }

    fun updateSearchText(text: String) {
        idlingRes.setIdleState(false)
        if (prevSearchTerm != text) {
            searchText.onNext(text)
            prevSearchTerm = text
        }
    }

    private fun getSearchResults() {
        searchText.debounce(600, TimeUnit.MILLISECONDS)
            .map {
                idlingRes.setIdleState(false)
                getMatchingCities(it)
            }
            .switchMapSingle {
                _cities.postValue(Resource.loading(null))
                Timber.d("Fetching current weather data for ${it.size} cities")
                idlingRes.setIdleState(false)
                currentWeatherRepo.getCurrentWeather(it, BuildConfig.OPEN_WEATHER_API_KEY)
            }
            .subscribeOn(io)
            .observeOn(ui)
            .subscribeBy(
                onError = {
                    _cities.value = Resource.error(data = null, error = it)
                    idlingRes.setIdleState(true)
                    getSearchResults()
                },
                onNext = {
                    _cities.value = Resource.success(data = it.cities)
                    idlingRes.setIdleState(true)
                }
            ).addTo(disposables)
    }

    private fun getMatchingCities(searchText: String): List<Int> {
        val cities = cityList.cities.filter {
            it.name.contains(other = searchText, ignoreCase = true)
        }.take(10).also { cities -> Timber.d("${cities.map { it.name }}") }
            .map { it.id }
        idlingRes.setIdleState(true)
        return cities
    }

    fun navigateToDetail(city: City) {
        navigator.sendNavigationEvent(DetailWeatherNavigationEvent(city))
    }

    fun sendToolbarUpdate(update: TextToolbarUpdate) {
        toolbarManager.sendToolbarUpdate(update)
    }

}
