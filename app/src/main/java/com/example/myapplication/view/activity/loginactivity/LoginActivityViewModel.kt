package com.example.myapplication.view.activity.loginactivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.to.UserRequest
import com.example.myapplication.usecase.account.Login

class LoginActivityViewModel(
    private val loginAccount: Login
): ViewModel() {
    var loginResponse = MutableLiveData<Boolean>()
    var apiError = MutableLiveData<Throwable>()

    fun login(email: String, password: String) {
        if(email.contains("@")){
            loginAccount.login(UserRequest(email, password, null, null) , {
                loginResponse.value = true
            }, {
                apiError.value = it
            })
        }else{
            loginAccount.login(UserRequest(null, password, email, null) , {
                loginResponse.value = true
            }, {
                apiError.value = it
            })
        }

    }
}