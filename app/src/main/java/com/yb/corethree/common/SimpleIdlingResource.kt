package com.yb.corethree.common

import androidx.test.espresso.IdlingResource
import java.util.concurrent.atomic.AtomicBoolean

object SimpleIdlingResource : IdlingResource {
    private val isIdle = AtomicBoolean(true)
    private var resourceCallback: IdlingResource.ResourceCallback? = null

    override fun getName(): String {
        return this.javaClass.simpleName
    }

    override fun isIdleNow(): Boolean {
        return isIdle.get()
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
        this.resourceCallback = callback
    }

    fun setIdleState(isIdleNow: Boolean) {
        isIdle.set(isIdleNow)
        if (resourceCallback != null) {
            if (isIdle.get()) resourceCallback!!.onTransitionToIdle()
        }
    }
}