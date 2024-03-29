package com.firan.githubapp.data.to

import java.util.*

/**
 * author: Artur Godlewski
 */
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