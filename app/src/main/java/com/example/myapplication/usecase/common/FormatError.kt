package com.example.myapplication.usecase.common

import android.content.Context
import com.example.myapplication.R
import com.example.myapplication.usecase.account.UserExistsException
import com.example.myapplication.usecase.account.WrongCredentialsException

class FormatError {
    companion object {
        fun perform(t: Throwable?, context: Context): String {
            return when (t) {
                null -> context.getString(R.string.error_unexpected)
                is UserExistsException -> context.getString(R.string.error_user_exists)
                is WrongCredentialsException -> context.getString(R.string.error_wrong_credentials)
                else -> t.localizedMessage ?: t.message ?: context.getString(R.string.error_unexpected)
            }
        }
    }
}