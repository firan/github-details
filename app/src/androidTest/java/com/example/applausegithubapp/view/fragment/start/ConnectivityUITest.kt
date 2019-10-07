/*
 * Copyright (c) 2019. For recrutation purposes only
 */

package com.example.applausegithubapp.view.fragment.start

import androidx.test.core.app.ActivityScenario
import com.example.applausegithubapp.R
import com.example.applausegithubapp.view.activity.ActivityTest
import com.example.applausegithubapp.view.activity.mainactivity.MainActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.applausegithubapp.mock.MockConnectivityCheck
import com.example.applausegithubapp.usecase.connection.ConnectionState
import com.example.applausegithubapp.usecase.connection.ConnectivityCheck
import com.example.applausegithubapp.view.getStartFragment
import org.hamcrest.Matchers
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.inject

/**
 * author: Artur Godlewski
 * UI tests - for check if offline row is displayed
 */
@RunWith(AndroidJUnit4::class)
class ConnectivityUITest : ActivityTest<MainActivity>() {
    private val mockConnectivityCheck: ConnectivityCheck by inject()

    @Test
    fun test_areResultsDisplayed() {
        (mockConnectivityCheck as MockConnectivityCheck).setConnectionState(ConnectionState.Offline)
        onView(
            Matchers.allOf(
                withId(R.id.offline_row),
                isDisplayed()
            )
        ).check(matches(isDisplayed()))
    }

    override fun startScenario(): ActivityScenario<MainActivity> {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        activityScenario.onActivity { activity ->
            val startFragment = getStartFragment(activity)
            this.idlingResource = (startFragment as StartFragment).fetcherListener
            IdlingRegistry.getInstance().register(this.idlingResource)
        }
        return activityScenario
    }
}
