package com.example.applausegithubapp.usecase.fetchrepository

import com.example.applausegithubapp.data.entity.GithubItem
import com.example.applausegithubapp.data.repository.GithubItemRepository
import com.example.applausegithubapp.data.to.GithubRepositoryRequest
import com.example.applausegithubapp.data.to.GithubRepositoryResponseItem
import com.example.applausegithubapp.webservice.APIClient
import com.example.applausegithubapp.webservice.APIInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.lang.Exception

/**
 * author: Artur Godlewski
 *
 * I feel that, I could spend on it more time and implement paging in recyclerView with calls to
 * service to make infinite scroll, but instruction description said only about 10 results,
 * so I can't load more because of break assumptions :)
 * Anyway I made parametrize page number and page size since github api is allowing those parameters
 *
 * Paging is easy, but it takes few more hours to apply it well using on View:
 *         val endlessRecyclerOnScrollListener = object : EndlessRecyclerOnScrollListener() {
 *               override fun onLoadMore() {
 *                   viewModel.loadNextPage()
 *               }
 *           }
 *           recyclerView.addOnScrollListener(endlessRecyclerOnScrollListener)
 *
 * And here I can take another page and append results
 */
class FetchRepositoryDetails(
    private val githubItemRepository: GithubItemRepository
) {
    companion object {
        const val UNPROCESSABLE_ENTITY = 422
        const val BAD_REQUEST = 400
    }

    fun perform(
        repositoryRequest: GithubRepositoryRequest,
        successHandler: () -> Unit,
        failureHandler: (Throwable?) -> Unit
    ) {
        val apiInterface = APIClient.client.create(APIInterface::class.java)
        val call = apiInterface.fetchRepositoryDetails(
            repositoryRequest.page.let { it }.run { 0 },
            repositoryRequest.pageSize.let { it }.run { 10 }
        )
        call.enqueue(object : Callback<List<GithubRepositoryResponseItem>> {
            override fun onFailure(call: Call<List<GithubRepositoryResponseItem>>, t: Throwable?) {
                if (t != null) {
                    Timber.e(t, "Repository fetch failure")
                }
                failureHandler(t)
            }

            override fun onResponse(
                call: Call<List<GithubRepositoryResponseItem>>?,
                response: Response<List<GithubRepositoryResponseItem>>
            ) {
                if (response.isSuccessful) {
                    Timber.v("Repository fetched successfully")
                    val body = response.body() as List<GithubRepositoryResponseItem>
                    storeRepositoryInfo(body)
                    successHandler()
                } else {
                    when (response.code()) {
                        BAD_REQUEST -> {
                            Timber.e("Bad request ${response.code()}")
                        }
                        UNPROCESSABLE_ENTITY -> {
                            Timber.e("Unprocessable entity ${response.code()}")
                        }
                        else -> {
                            Timber.e("Repository fetch failed, error code: ${response.code()}")
                        }
                    }
                    failureHandler(Exception(response.code().toString()))
                    return
                }
            }
        })
    }

    private fun storeRepositoryInfo(response: List<GithubRepositoryResponseItem>) {
        val githubItems = response.map { responseItem ->
            GithubItem(
                identifier = responseItem.id,
                name = responseItem.name,
                private = responseItem.private,
                description = responseItem.description,
                url = responseItem.html_url,
                createdAt = responseItem.created_at,
                updatedAt = responseItem.updated_at,
                language = responseItem.language,
                hasIssues = responseItem.has_issues
            )
        }
        githubItemRepository.save(githubItems)
    }
}