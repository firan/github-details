package com.example.applausegithubapp.view.fragment.start

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.applausegithubapp.data.repository.GithubItemRepository
import com.example.applausegithubapp.data.to.GithubRepositoryRequest
import com.example.applausegithubapp.data.to.NameTuple
import com.example.applausegithubapp.usecase.repository.FetchRepositoryDetails

class StartFragmentViewModel(
    private val fetchRepositories: FetchRepositoryDetails,
    githubItemRepository: GithubItemRepository
) : ViewModel() {
    var repositories: LiveData<List<NameTuple>> =
        githubItemRepository.findGithubItemsNames()
    var apiError = MutableLiveData<Throwable>()
    var filterRegex = ""
    val filteredRepositories = MutableLiveData<List<NameTuple>>()

    fun fetchRepositories() {
        fetchRepositories.perform(GithubRepositoryRequest(0, 10), {
        }, {
            apiError.value = it
        })
    }

    fun onRefresh() {
        fetchRepositories()
    }

    fun filter(filter: String) {
        filterRegex = filter
        filteredRepositories.postValue(repositories.value?.filter { item ->
            item.name.contains(
                filter,
                true
            )
        })
    }
}