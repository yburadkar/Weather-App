package com.yb.openweather.common

/**
 * Wrapper for data that is exposed via LiveData that represents a ui event
 * Get content will return the event content once and only once
 */
class UIEvent<T>(
    private val content: T
) {
    var hasBeenHandled = false
        private set

    fun getContent(): T? =
        if (hasBeenHandled)
            null
        else {
            hasBeenHandled = true
            content
        }

    fun peekContent(): T = content

}