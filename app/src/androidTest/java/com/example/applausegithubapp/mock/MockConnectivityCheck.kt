package com.example.applausegithubapp.mock

import androidx.lifecycle.MutableLiveData
import com.example.applausegithubapp.extension.getValueBlocking
import com.example.applausegithubapp.usecase.connection.ConnectionState
import com.example.applausegithubapp.usecase.connection.ConnectivityCheck

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