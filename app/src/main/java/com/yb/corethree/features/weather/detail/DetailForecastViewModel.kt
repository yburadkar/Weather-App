package com.yb.corethree.features.weather.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yb.corethree.BuildConfig
import com.yb.corethree.common.DisposingViewModel
import com.yb.corethree.common.Resource
import com.yb.corethree.domain.repos.IDetailForecastRepository
import com.yb.corethree.mappers.DetailForecastMapper
import com.yb.corethree.models.ForecastItem
import io.reactivex.Scheduler
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject
import javax.inject.Named

class DetailForecastViewModel @Inject constructor(
    private val detailForecastRepo: IDetailForecastRepository,
    @Named("io") private val io: Scheduler,
    @Named("ui") private val ui: Scheduler
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

}