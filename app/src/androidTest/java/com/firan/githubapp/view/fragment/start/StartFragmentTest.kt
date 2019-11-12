package com.firan.githubapp.view.fragment.start

import androidx.test.core.app.ActivityScenario
import com.firan.githubapp.R
import com.firan.githubapp.view.activity.ActivityTest
import com.firan.githubapp.view.activity.mainactivity.MainActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.firan.githubapp.view.getStartFragment
import com.firan.githubapp.view.isKeyboardShown
import junit.framework.TestCase
import org.hamcrest.Matchers
import org.junit.Test
import org.junit.runner.RunWith

/**
 * author: Artur Godlewski
 * The UI in this sample is very simple, so I just made a test
 * of focus on search input, showed how can I wait for asyncs using IdlingResource than just
 * Thread.sleep and I made in the DetailsFragmentTest simple transition test
 *
 * Please treat it just like a showoff of e2e test ability with handling async operation
 */
@RunWith(AndroidJUnit4::class)
class StartFragmentTest : ActivityTest<MainActivity>() {

    @Test
    fun test_ShouldOpenKeyboardWhenClickOnSearchBar() {
        onView(withId(R.id.repoNameValue)).perform(click())
        TestCase.assertTrue(isKeyboardShown())
    }

    @Test
    fun test_areResultsDisplayed() {
        onView(
            Matchers.allOf(
                withId(R.id.searchItems),
                ViewMatchers.isDisplayingAtLeast(1)
            )
        ).check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
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
