package com.firan.githubapp.usecase.common

import android.content.Context
import com.firan.githubapp.R

/**
 * author: Artur Godlewski
 * can localize error messages
 */
class FormatError {
    companion object {
        fun perform(t: Throwable?, context: Context): String {
            return when (t) {
                null -> context.getString(R.string.error_unexpected)
                else -> t.localizedMessage ?: t.message ?: context.getString(R.string.error_unexpected)
            }
        }
    }
}