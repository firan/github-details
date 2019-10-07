package com.example.applausegithubapp

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest

/**
 * author: Artur Godlewski
 */
@ExperimentalCoroutinesApi
fun <T> runTest(block: suspend () -> T) {
    runBlockingTest { block() }
}
