package com.example.applausegithubapp.usecase.connection

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.MutableLiveData
import timber.log.Timber

/**
 * author: Artur Godlewski
 * thing for show offline warning on main screen
 * implemented it as a bonus
 */
class ConnectivityCheck(context: Context) {

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val connectionLiveData = MutableLiveData<ConnectionState>()

    /**
     * this can be used for check connection state on demand
     */
    fun perform(): Boolean {
        if (Build.VERSION.SDK_INT < 23) {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null) {
                return activeNetworkInfo.isConnected
            }
        } else {
            val activeNetwork = connectivityManager.activeNetwork
            if (activeNetwork != null) {
                val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
                return networkCapabilities!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || networkCapabilities.hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI
                )
            }
        }
        return false

    }

    private var networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onLost(network: Network?) {
            connectionLiveData.postValue(ConnectionState.Offline)
        }

        override fun onUnavailable() {
            connectionLiveData.postValue(ConnectionState.Offline)
        }

        override fun onLosing(network: Network?, maxMsToLive: Int) {
            /**
             * do nothing
             */
        }

        override fun onAvailable(network: Network?) {
            connectionLiveData.postValue(ConnectionState.Online)
        }
    }

    init {
        val networkRequest = NetworkRequest.Builder().build()
        try {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        } catch (e: RuntimeException) {
            /**
             * unregister when already registered
             */
            Timber.v("Already unregistered")
        }
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

}