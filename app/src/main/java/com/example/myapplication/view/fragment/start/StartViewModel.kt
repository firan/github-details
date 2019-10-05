package com.example.myapplication.view.fragment.start

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.entity.User
import com.example.myapplication.data.repository.UserRepository

class StartViewModel(
    private val userRepository : UserRepository
) : ViewModel() {
    lateinit var userAccounts: LiveData<List<User>>

    fun loadUserData() {
        userAccounts = userRepository.findUserAccounts()
    }
}