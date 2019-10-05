package com.example.myapplication.view.activity.loginactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.myapplication.R
import com.example.myapplication.usecase.common.FormatError
import com.example.myapplication.usecase.common.hideProgressDialog
import com.example.myapplication.usecase.common.showProgressDialog
import com.example.myapplication.view.activity.mainactivity.MainActivity
import com.example.myapplication.view.activity.registeractivity.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val model: LoginActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonRegister.setOnClickListener {
            val activityIntent = Intent(this, RegisterActivity::class.java)
            startActivity(activityIntent)
        }

        supportActionBar?.hide()
        attachObserver()
    }

    private fun attachObserver() {
        model.apiError.observe(this, Observer { throwable ->
            throwable?.let { error ->
                Toast.makeText(applicationContext, FormatError.perform(error, applicationContext), Toast.LENGTH_LONG).show()
                hideProgressDialog()
            }
        })

        model.loginResponse.observe(this, Observer { success ->
            success?.let {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        })
    }

    @Suppress("UNUSED_PARAMETER")
    fun loginButtonTapped(view: View) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)

        val username = enterUsernameOrEmail.text.toString().trim()
        val password = enterPassword.text.toString().trim()

        if (username.isNullOrEmpty() || password.isNullOrEmpty()) {
            Toast.makeText(applicationContext, "Please fill all fields.", Toast.LENGTH_LONG).show()
        } else {
            performLogin(username, password)
        }
    }

    private fun performLogin(username: String, password: String) {
        showProgressDialog(this)
        model.login(username, password)
    }
}
