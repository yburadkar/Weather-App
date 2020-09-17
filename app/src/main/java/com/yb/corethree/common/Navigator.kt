package com.yb.corethree.common

import io.reactivex.subjects.PublishSubject

object Navigator {

    private val navigationSubject = PublishSubject.create<NavigationEvent>()

    fun onNavigationEvent(): io.reactivex.Observable<NavigationEvent> {
        return navigationSubject
    }

    fun sendNavigationEvent(event: NavigationEvent) {
        navigationSubject.onNext(event)
    }

}

sealed class NavigationEvent
object SearchWeatherNavigationEvent : NavigationEvent()
class DetailWeatherNavigationEvent(val cityId: Int) : NavigationEvent()
