package com.example.myapplication.data.to

data class UserRequest(
    val email: String?,
    val password: String?,
    val userName: String?,
    val token: String?
)