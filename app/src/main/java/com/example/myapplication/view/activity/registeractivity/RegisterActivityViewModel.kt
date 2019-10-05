package com.example.myapplication.view.activity.registeractivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.to.UserRequest
import com.example.myapplication.usecase.account.RegisterAccount

class RegisterActivityViewModel(
    private val registerAccount: RegisterAccount
): ViewModel() {
    var registerResponse = MutableLiveData<Boolean>()
    var apiError = MutableLiveData<Throwable>()

    fun registerAccount(email: String, password: String, username: String) {
        registerAccount.register(UserRequest(email, password, username, null) , {
            registerResponse.value = true
        }, {
            apiError.value = it
        })
    }
}