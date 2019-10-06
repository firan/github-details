package com.example.applausegithubapp.usecase.connection

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import org.koin.core.KoinComponent

class ConnectivityCheck(private val context: Context) {

    fun perform(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        activeNetwork ?: return false
        return activeNetwork.isConnected
    }

    inner class ConnectionLiveData : LiveData<ConnectionState>() {

        override fun onActive() {
            super.onActive()
            val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            context.registerReceiver(networkReceiver, filter)
        }

        override fun onInactive() {
            super.onInactive()
            context.unregisterReceiver(networkReceiver)
        }

        private val networkReceiver = object : BroadcastReceiver(), KoinComponent {
            override fun onReceive(context: Context, intent: Intent) {
                if (perform()) {
                    postValue(ConnectionState.Online)
                } else {
                    postValue(ConnectionState.Offline)
                }
            }
        }
    }
}