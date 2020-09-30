package com.yb.openweather.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yb.openweather.features.main.MainViewModel
import com.yb.openweather.features.weather.detail.DetailForecastViewModel
import com.yb.openweather.features.weather.search.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailForecastViewModel::class)
    abstract fun bindDetailForecastViewModel(viewModel: DetailForecastViewModel): ViewModel

}