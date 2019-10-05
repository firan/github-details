package com.example.myapplication.view.activity.splashscreen

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_splash_screen.*
import android.content.Intent
import com.example.myapplication.view.activity.loginactivity.LoginActivity


class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()

        splashScreenAnimation.addAnimatorListener(createAnimatorListener())

        splashScreenAnimation.playAnimation()
    }

    private fun createAnimatorListener(): Animator.AnimatorListener {
        return object : AnimatorListenerAdapter() {
            override fun onAnimationCancel(animation: Animator?) {
                splashScreenAnimation.isVisible = false
            }

            override fun onAnimationStart(animation: Animator?) {
                splashScreenAnimation.isVisible = true
            }

            override fun onAnimationEnd(animation: Animator) {
                val i = Intent(this@SplashScreenActivity, LoginActivity::class.java)
                startActivity(i)
                finish()
            }
        }
    }
}
