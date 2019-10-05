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
    val url: String?,
    val createdAt: Date,
    val updatedAt: Date?,
    val language: String?,
    val hasIssues: Boolean?
)