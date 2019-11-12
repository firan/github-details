package com.firan.githubapp.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.test.platform.app.InstrumentationRegistry
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * https://github.com/slomin/Testing-Room-LiveData/blob/master/app/src/androidTest/java/com/kotlinblog/roomlivedata/extensions/LiveDataExtensions.kt
 *
 * This function blocks the thread, waits for the value to be passed to observer and then returns it
 */
@Throws(InterruptedException::class)
fun <T> LiveData<T>.getValueBlocking(): T? {
    var value: T? = null
    val latch = CountDownLatch(1)
    val innerObserver = Observer<T> {
        value = it
        latch.countDown()
    }
    try {
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            observeForever(innerObserver)
        }
    } catch (e: Exception) {
        observeForever(innerObserver)
    }

    latch.await(2, TimeUnit.SECONDS)
    return value
}