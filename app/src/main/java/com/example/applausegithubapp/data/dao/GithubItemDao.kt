package com.example.applausegithubapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.applausegithubapp.data.entity.GithubItem
import com.example.applausegithubapp.data.to.NameTuple

/**
 * author: Artur Godlewski
 */
@Dao
interface GithubItemDao {
    @Query("SELECT * FROM githubitem")
    fun getAll(): LiveData<List<GithubItem>>

    @Query("SELECT rid, name FROM githubitem")
    fun getAllNames(): LiveData<List<NameTuple>>

    @Query("SELECT * FROM githubitem WHERE rid = :rid LIMIT 1")
    fun findById(rid: Int): LiveData<GithubItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(githubItems: List<GithubItem>)

    @Delete
    fun delete(githubItem: GithubItem)
}
