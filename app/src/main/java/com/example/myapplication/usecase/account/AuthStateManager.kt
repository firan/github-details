package com.example.myapplication.usecase.account

class AuthStateManager(private val secureStorage: SecureStorage) {
    companion object {
        private const val AUTH_STATE_KEY = "auth_state"
        private val lock = Object()
    }

    fun hasState() = load() != null

    fun update(token: String) {
        synchronized(lock) {
            secureStorage.set(token, AUTH_STATE_KEY)
        }
    }

    fun load(): String? {
        synchronized(lock) {
            return secureStorage.stringFor(AUTH_STATE_KEY) ?: return null
        }
    }

    fun clear() {
        secureStorage.remove(AUTH_STATE_KEY)
    } }