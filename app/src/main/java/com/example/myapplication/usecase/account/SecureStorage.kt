package com.example.myapplication.usecase.account

import android.content.Context

interface SecureStorage {
    fun stringFor(key: String): String?
    fun set(value: String?, key: String)
    fun remove(key: String)
}

private const val AUTH_STATE_KEY = "auth_state"

class SecureSharedPrefsStorage(private val context: Context) : SecureStorage {
    private val sharedPreferences by lazy {
        context.getSharedPreferences(AUTH_STATE_KEY, Context.MODE_PRIVATE)
    }

    override fun stringFor(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    override fun set(value: String?, key: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    override fun remove(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }
}