package com.firan.githubapp.webservice

import com.firan.githubapp.data.to.GithubRepositoryResponseItem
import retrofit2.Call
import retrofit2.http.*

/**
 * author: Artur Godlewski
 */
interface APIInterface {
    @GET("/users/firan/repos?")
    fun fetchRepositoryDetails(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Call<List<GithubRepositoryResponseItem>>
}