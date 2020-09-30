package com.yb.openweather.features.weather.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yb.openweather.BuildConfig
import com.yb.openweather.common.DisposingViewModel
import com.yb.openweather.common.Resource
import com.yb.openweather.common.TextToolbarUpdate
import com.yb.openweather.common.ToolbarManager
import com.yb.openweather.domain.repos.IDetailForecastRepository
import com.yb.openweather.mappers.DetailForecastMapper
import com.yb.openweather.models.ForecastItem
import io.reactivex.Scheduler
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject
import javax.inject.Named

class DetailForecastViewModel @Inject constructor(
    private val detailForecastRepo: IDetailForecastRepository,
    @Named("io") private val io: Scheduler,
    @Named("ui") private val ui: Scheduler,
    private val toolbarManager: ToolbarManager
) : DisposingViewModel() {

    private val _forecasts = MutableLiveData<Resource<List<ForecastItem>>>()
    val forecasts: LiveData<Resource<List<ForecastItem>>> = _forecasts

    init {
        _forecasts.value = Resource.success(data = emptyList())
    }

    fun getDetailForecast(cityId: Int) {
        detailForecastRepo.getDetailForecast(cityId, BuildConfig.OPEN_WEATHER_API_KEY)
            .doOnSubscribe { _forecasts.postValue(Resource.loading(_forecasts.value?.data)) }
            .subscribeOn(io)
            .observeOn(ui)
            .subscribeBy(
                onError = {
                    _forecasts.value = Resource.error(data = _forecasts.value?.data, error = it)
                },
                onSuccess = {
                    _forecasts.value = Resource.success(data = DetailForecastMapper.map(it.forecasts))
                }
            ).addTo(disposables)

    }

    fun sendToolbarUpdate(update: TextToolbarUpdate) {
        toolbarManager.sendToolbarUpdate(update)
    }

}