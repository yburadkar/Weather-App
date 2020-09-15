package com.yb.corethree.common

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

object ToolbarManager {

    private val toolbarSubject = PublishSubject.create<ToolbarUpdate>()

    fun sendToolbarUpdate(update: ToolbarUpdate) {
        toolbarSubject.onNext(update)
    }

    fun onToolbarEvent(): Observable<ToolbarUpdate> {
        return toolbarSubject
    }

}

sealed class ToolbarUpdate

class TextToolbarUpdate(val title: String, val showBack: Boolean) : ToolbarUpdate()