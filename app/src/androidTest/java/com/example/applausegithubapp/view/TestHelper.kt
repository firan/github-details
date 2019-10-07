package com.example.applausegithubapp.view

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.test.platform.app.InstrumentationRegistry
import com.example.applausegithubapp.*
import com.example.applausegithubapp.view.activity.mainactivity.MainActivity
import com.example.applausegithubapp.view.fragment.start.StartFragment

/**
 * author: Artur Godlewski
 */
fun isKeyboardShown(): Boolean {
    val inputMethodManager =
        InstrumentationRegistry.getInstrumentation().targetContext.getSystemService(
            Context.INPUT_METHOD_SERVICE
        ) as InputMethodManager
    return inputMethodManager.isAcceptingText
}

fun getStartFragment(activity: Activity): Fragment? {
    val fragment =
        (activity as MainActivity).supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
    val currentFragment = fragment?.childFragmentManager?.fragments?.firstOrNull()
    return if (currentFragment != null && currentFragment is StartFragment) {
        currentFragment
    } else {
        null
    }
}