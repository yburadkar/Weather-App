package com.yb.corethree.features.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yb.corethree.common.DisposingViewModel
import com.yb.corethree.common.NavigationEvent
import com.yb.corethree.common.Navigator
import com.yb.corethree.common.SearchWeatherNavigationEvent
import com.yb.corethree.common.ToolbarManager
import com.yb.corethree.common.ToolbarUpdate
import com.yb.corethree.common.UIEvent
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val navigator: Navigator,
    private val toolbarManager: ToolbarManager
) : DisposingViewModel() {

    private val _navEvent = MutableLiveData<UIEvent<NavigationEvent>>()
    val navEvent: LiveData<UIEvent<NavigationEvent>> = _navEvent

    private val _toolbarEvent = MutableLiveData<UIEvent<ToolbarUpdate>>()
    val toolbarEvent: LiveData<UIEvent<ToolbarUpdate>> = _toolbarEvent

    init {
        onNavEvent()
        onToolbarEvent()
    }

    private fun onNavEvent() {
        navigator.onNavigationEvent()
            .subscribeBy(Timber::e) {
                _navEvent.value = UIEvent(it)
            }
            .addTo(disposables)
    }

    private fun onToolbarEvent() {
        toolbarManager.onToolbarEvent()
            .subscribeBy(Timber::e) {
                _toolbarEvent.value = UIEvent(it)
            }.addTo(disposables)
    }

    fun navigateToSearchScreen() {
        navigator.sendNavigationEvent(SearchWeatherNavigationEvent)
    }

}