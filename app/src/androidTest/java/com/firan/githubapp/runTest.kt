package com.firan.githubapp

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest

/**
 * author: Artur Godlewski
 */
@ExperimentalCoroutinesApi
fun <T> runTest(block: suspend () -> T) {
    runBlockingTest { block() }
}
