package com.firan.githubapp.usecase.common

import androidx.test.espresso.IdlingResource

/**
 * author: Artur Godlewski
 */
class FetchingIdlingResource: IdlingResource, FetcherListener {
    private var idle = true
    private var resourceCallback:
            IdlingResource.ResourceCallback? = null

    override fun getName(): String {
        return FetchingIdlingResource::class.java.simpleName
    }

    override fun isIdleNow() = idle

    override fun registerIdleTransitionCallback(
        callback: IdlingResource.ResourceCallback?) {
        resourceCallback = callback
    }

    override fun doneFetching() {
        idle = true
        resourceCallback?.onTransitionToIdle()
    }

    override fun beginFetching() {
        idle = false
    }
}