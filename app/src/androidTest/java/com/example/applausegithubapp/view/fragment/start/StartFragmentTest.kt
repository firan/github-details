package com.example.applausegithubapp.view.fragment.start

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import com.example.applausegithubapp.R
import com.example.applausegithubapp.view.activity.ActivityTest
import com.example.applausegithubapp.view.activity.mainactivity.MainActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StartFragmentTest : ActivityTest<MainActivity>() {

    @Test
    fun testShouldOpenKeyboardWhenClickOnSearchBar() {
        Thread.sleep(1000)
        onView(withId(R.id.repoNameValue)).perform(click())
        assertEquals(true, isKeyboardShown())
    }

    override fun startScenario(): ActivityScenario<MainActivity> {
        return launchActivity()
    }

    private fun isKeyboardShown(): Boolean {
        val inputMethodManager = InstrumentationRegistry.getInstrumentation().targetContext.getSystemService(
            Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.isAcceptingText
    }
}
