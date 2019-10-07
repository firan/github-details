package com.example.applausegithubapp.data.repository

import androidx.lifecycle.LiveData
import com.example.applausegithubapp.data.dao.GithubItemDao
import com.example.applausegithubapp.data.entity.GithubItem
import com.example.applausegithubapp.data.to.NameTuple
import java.util.concurrent.Executor

/**
 * author: Artur Godlewski
 */
interface GithubItemRepository {
    fun save(githubItems: List<GithubItem>)
    fun findById(id: Int): LiveData<GithubItem>
    fun findGithubItemsNames(): LiveData<List<NameTuple>>
}

class GithubItemRepositoryImpl(
    private val githubItemDao: GithubItemDao,
    private val diskIOExecutor: Executor
) : GithubItemRepository {
    override fun save(githubItems: List<GithubItem>) {
        diskIOExecutor.execute {
            githubItemDao.insertAll(githubItems)
        }
    }

    override fun findById(id: Int): LiveData<GithubItem> {
        return githubItemDao.findById(id)
    }

    override fun findGithubItemsNames(): LiveData<List<NameTuple>> {
        return githubItemDao.getAllNames()
    }
}