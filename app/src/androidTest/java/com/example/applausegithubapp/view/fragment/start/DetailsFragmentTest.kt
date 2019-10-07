package com.example.applausegithubapp.view.fragment.start

import androidx.test.core.app.ActivityScenario
import com.example.applausegithubapp.R
import com.example.applausegithubapp.view.activity.ActivityTest
import com.example.applausegithubapp.view.activity.mainactivity.MainActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.applausegithubapp.view.getStartFragment
import org.hamcrest.Matchers
import org.junit.Test
import org.junit.runner.RunWith

/**
 * author: Artur Godlewski
 */
@RunWith(AndroidJUnit4::class)
class DetailsFragmentTest : ActivityTest<MainActivity>() {

    @Test
    fun test_isTransitionToDetailsDone() {
        onView(Matchers.allOf(withId(R.id.searchItems), ViewMatchers.isDisplayingAtLeast(1)))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
            .perform(RecyclerViewActions.actionOnItemAtPosition<StartFragmentListAdapter.ViewHolder>(0, click()))

        onView(
            Matchers.allOf(
                withId(R.id.header),
                ViewMatchers.isDisplayed()
            )
        )
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
