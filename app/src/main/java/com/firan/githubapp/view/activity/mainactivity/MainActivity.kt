package com.firan.githubapp.view.activity.mainactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.firan.githubapp.R
import com.firan.githubapp.usecase.common.convertDPToPixel
import com.firan.githubapp.view.fragment.interfaces.ChildFragment
import com.firan.githubapp.view.fragment.interfaces.FlatActionBar
import com.firan.githubapp.view.fragment.interfaces.FragmentDialog

/**
 * author: Artur Godlewski
 */
class MainActivity : AppCompatActivity() {

    private val navController by lazy { findNavController(R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNavigation()
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()

    private val fragmentHandler = object : FragmentManager.FragmentLifecycleCallbacks() {

        override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
            super.onFragmentResumed(fm, f)
            if (f is FlatActionBar && f !is NavHostFragment) {
                supportActionBar?.elevation = 0F
            } else if (f !is NavHostFragment) {
                supportActionBar?.elevation = applicationContext.convertDPToPixel(8).toFloat()
            }
            when (f) {
                is FragmentDialog -> {
                    if (f is ChildFragment) {
                        supportActionBar?.setHomeAsUpIndicator(null)
                    } else {
                        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
                    }

                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                is ChildFragment -> {
                    supportActionBar?.setHomeAsUpIndicator(null)
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                is NavHostFragment -> {}
                else -> {
                    supportActionBar?.setHomeAsUpIndicator(null)
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
            }
        }
    }

    private fun initNavigation() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            supportActionBar?.title = destination.label
        }
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentHandler, true)
    }
}
