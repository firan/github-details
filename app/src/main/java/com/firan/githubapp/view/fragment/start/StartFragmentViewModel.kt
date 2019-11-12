package com.firan.githubapp.view.fragment.start

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firan.githubapp.data.repository.GithubItemRepository
import com.firan.githubapp.data.to.GithubRepositoryRequest
import com.firan.githubapp.data.to.NameTuple
import com.firan.githubapp.usecase.connection.ConnectivityCheck
import com.firan.githubapp.usecase.fetchrepository.FetchRepositoryDetails

/**
 * author: Artur Godlewski
 */
class StartFragmentViewModel(
    private val fetchRepositories: FetchRepositoryDetails,
    githubItemRepository: GithubItemRepository,
    connectivityCheck: ConnectivityCheck
) : ViewModel() {
    val filteredRepositories = MutableLiveData<List<NameTuple>>()
    val isRefreshing = MutableLiveData<Boolean>()
    val repositories: LiveData<List<NameTuple>> =
        githubItemRepository.findGithubItemsNames()
    val connection = connectivityCheck.connectionLiveData
    val apiError = MutableLiveData<Throwable>()

    fun onRefresh() {
        isRefreshing.postValue(true)
        fetchRepositories.perform(GithubRepositoryRequest(0, 10), {
            isRefreshing.postValue(false)
        }, {
            apiError.value = it
            isRefreshing.postValue(false)
        })
    }

    fun filter(filter: String) {
        filteredRepositories.postValue(repositories.value?.filter { item ->
            item.name.contains(
                filter,
                true
            )
        })
    }
}