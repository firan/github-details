package com.example.applausegithubapp.data.to

import java.util.*

data class GithubRepositoryResponse(
    val body: List<GithubRepositoryResponseItem>
)

data class GithubRepositoryResponseItem(
    val id: String,
    val name: String,
    val private: Boolean,
    val description: String?,
    val html_url: String?,
    val created_at: Date,
    val updated_at: Date?,
    val language: String?,
    val has_issues: Boolean?
)