package com.firan.githubapp.view.fragment.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.firan.githubapp.data.entity.GithubItem
import com.firan.githubapp.data.repository.GithubItemRepository

/**
 * author: Artur Godlewski
 */
class DetailsFragmentViewModel(
    repoId: Int,
    githubItemRepository: GithubItemRepository
) : ViewModel() {
    var repository: LiveData<GithubItem> = githubItemRepository.findById(repoId)
}