package com.firan.githubapp

import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * author: Artur Godlewski
 */
class AsyncExpectation {
    private val signal = CountDownLatch(1)

    fun fulfill() {
        signal.countDown()
    }

    fun await() = signal.await(3, TimeUnit.SECONDS)
}