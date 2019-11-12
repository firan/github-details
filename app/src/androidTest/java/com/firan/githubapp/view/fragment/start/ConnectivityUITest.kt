package com.firan.githubapp.view.fragment.start

import androidx.test.core.app.ActivityScenario
import com.firan.githubapp.R
import com.firan.githubapp.view.activity.ActivityTest
import com.firan.githubapp.view.activity.mainactivity.MainActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.firan.githubapp.mock.MockConnectivityCheck
import com.firan.githubapp.usecase.connection.ConnectionState
import com.firan.githubapp.usecase.connection.ConnectivityCheck
import com.firan.githubapp.view.getStartFragment
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
    fun test_isOfflineHintDisplayed() {
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
