package com.example.myapplication.usecase.account

import com.example.myapplication.data.entity.User
import com.example.myapplication.data.repository.UserRepository
import com.example.myapplication.data.to.UserRequest
import com.example.myapplication.data.to.UserResponse
import com.example.myapplication.webservice.APIClient
import com.example.myapplication.webservice.APIInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.lang.Exception
import java.util.*
import java.util.concurrent.Executor

class Login(
    private val authStateManager: AuthStateManager,
    private val userRepository: UserRepository,
    private val diskIOExecutor: Executor
) {
    companion object {
        const val USER_NOT_FOUND_CODE = 404
        const val BAD_REQUEST = 400
    }

    fun login(userRequest: UserRequest, successHandler: () -> Unit, failureHandler: (Throwable?) -> Unit) {
        val apiInterface = APIClient.client.create(APIInterface::class.java)
        val call = apiInterface.login(userRequest)
        call.enqueue(object : Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable?) {
                if (t != null) {
                    Timber.e(t, "LoginWebservice failure")
                }
                failureHandler(t)
            }

            override fun onResponse(call: Call<UserResponse>?, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    Timber.v("Logged in successfully")
                    val body = response.body() as UserResponse
                    storeAuthState(body)
                    storeAccountInformation(body)
                    successHandler()
                } else {
                    when (response.code()) {
                        USER_NOT_FOUND_CODE -> {
                            Timber.e("Login failed, wrong e-mail/username or password ${response.code()}")
                            failureHandler(WrongCredentialsException())
                            return
                        }
                        BAD_REQUEST -> {
                            Timber.e("Bad request ${response.code()}")
                            failureHandler(Exception(response.code().toString()))
                            return
                        }
                        else -> {
                            Timber.e("Login failed, wrong e-mail/username or password ${response.code()}")
                            failureHandler(Exception(response.code().toString()))
                            return
                        }
                    }
                }
            }

        })
    }

    private fun storeAuthState(response: UserResponse) {
        authStateManager.update(response.token ?: return)
    }

    private fun storeAccountInformation(response: UserResponse) {
        val userId = response.userId ?: return
        val user = User(response.userName, response.email, userId, Date())
        diskIOExecutor.execute {
            userRepository.registerUser(user)
        }
    }
}