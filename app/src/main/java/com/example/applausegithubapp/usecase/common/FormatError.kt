package com.example.applausegithubapp.usecase.common

import android.content.Context
import com.example.applausegithubapp.R

class FormatError {
    companion object {
        fun perform(t: Throwable?, context: Context): String {
            return when (t) {
                null -> context.getString(R.string.error_unexpected)
//                is UnkownHostException -> context.getString(R.string.error_unknown_host)
                else -> t.localizedMessage ?: t.message ?: context.getString(R.string.error_unexpected)
            }
        }
    }
}