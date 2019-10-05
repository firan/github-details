package com.example.myapplication.webservice

import com.example.myapplication.data.to.UserRequest
import com.example.myapplication.data.to.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface APIInterface {

    @POST("/api/v1/register")
    fun register(@Body userRequest: UserRequest): Call<UserResponse>

    @POST("/api/v1/login")
    fun login(@Body userRequest: UserRequest): Call<UserResponse>

    @PUT("/api/v1/updateUsername")
    fun updateUsername(@Body userRequest: UserRequest): Call<UserResponse>

    @PUT("/api/v1/updatePassword")
    fun updatePassword(@Body userRequest: UserRequest): Call<UserResponse>

    @DELETE("/api/v1/deleteAccount")
    fun deleteAccount(@Body userRequest: UserRequest): Call<Void>
}