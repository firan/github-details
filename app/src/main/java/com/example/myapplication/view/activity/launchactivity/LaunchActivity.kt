package com.example.myapplication.view.activity.launchactivity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.usecase.account.AuthStateManager
import com.example.myapplication.view.activity.mainactivity.MainActivity
import com.example.myapplication.view.activity.splashscreen.SplashScreenActivity
import org.koin.android.ext.android.inject

class LaunchActivity: AppCompatActivity() {
    private val authStateManager: AuthStateManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityIntent = if (authStateManager.hasState()) {
            Intent(this, MainActivity::class.java)
        } else {
            Intent(this, SplashScreenActivity::class.java)
        }

        activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        activityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        activityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        startActivity(activityIntent)
        finish()
    }
}