package com.example.applausegithubapp.view.fragment.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.applausegithubapp.data.entity.GithubItem
import com.example.applausegithubapp.data.repository.GithubItemRepository

/**
 * author: Artur Godlewski
 */
class DetailsFragmentViewModel(
    repoId: Int,
    githubItemRepository: GithubItemRepository
) : ViewModel() {
    var repository: LiveData<GithubItem> = githubItemRepository.findById(repoId)
}