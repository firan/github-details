package com.example.applausegithubapp.usecase.fetchrepository

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.applausegithubapp.AppDatabase
import com.example.applausegithubapp.AsyncExpectation
import com.example.applausegithubapp.data.repository.GithubItemRepository
import com.example.applausegithubapp.data.repository.GithubItemRepositoryImpl
import com.example.applausegithubapp.data.to.GithubRepositoryRequest
import com.example.applausegithubapp.extension.getValueBlocking
import com.example.applausegithubapp.runTest
import com.example.applausegithubapp.usecase.SynchronousExecutor
import com.example.applausegithubapp.usecase.getTestDatabase
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * author: Artur Godlewski
 * unit test with waiting for async db operation
 * in this test I'm testing whole main flow
 * download from API, also repository save and load functions.
 * This can be defragmented and mocked partially in separated tests,
 * so mock actual call in repository tests and mock repos in call test
 * but I didn't want to waste much time, as this is only skills showoff
 */
@RunWith(AndroidJUnit4::class)
class FetchRepositoryDetailsTest {

    private lateinit var fetchRepositoryDetails: FetchRepositoryDetails
    private lateinit var githubItemRepository: GithubItemRepository
    private lateinit var testDatabase: AppDatabase

    @Before
    fun setUp() {
        val executor = SynchronousExecutor()
        testDatabase = getTestDatabase()
        githubItemRepository = GithubItemRepositoryImpl(testDatabase.githubItemDao(), executor)
        fetchRepositoryDetails = FetchRepositoryDetails(
            githubItemRepository
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun test_shouldDownloadRepositoriesInfo() = runTest {
        val asyncExpectation = AsyncExpectation()
        fetchRepositoryDetails.perform(GithubRepositoryRequest(0, 10), {
            asyncExpectation.fulfill()
        }, {
            Assert.fail()
        })
        asyncExpectation.await()
        TestCase.assertEquals(
            10,
            githubItemRepository.findGithubItemsNames().getValueBlocking()?.size
        )
    }
}