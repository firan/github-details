package com.firan.githubapp.mock

import androidx.lifecycle.MutableLiveData
import com.firan.githubapp.extension.getValueBlocking
import com.firan.githubapp.usecase.connection.ConnectionState
import com.firan.githubapp.usecase.connection.ConnectivityCheck

/**
 * author: Artur Godlewski
 * thing for show offline warning on main screen
 * mock service
 */
class MockConnectivityCheck: ConnectivityCheck {
    override val connectionLiveData = MutableLiveData<ConnectionState>()

    override fun perform(): Boolean {
        return connectionLiveData.getValueBlocking()?.equals(ConnectionState.Online) ?: false
    }

    fun setConnectionState(connectionState: ConnectionState) {
        connectionLiveData.postValue(connectionState)
    }
}