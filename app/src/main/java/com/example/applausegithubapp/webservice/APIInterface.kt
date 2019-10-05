package com.example.applausegithubapp.webservice

import com.example.applausegithubapp.data.to.GithubRepositoryResponseItem
import retrofit2.Call
import retrofit2.http.*

interface APIInterface {
    @GET("/orgs/applauseoss/repos?")
    fun fetchRepositoryDetails(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Call<List<GithubRepositoryResponseItem>>
}