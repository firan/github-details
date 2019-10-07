package com.example.applausegithubapp

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.applausegithubapp.data.converters.Converters
import com.example.applausegithubapp.data.dao.GithubItemDao
import com.example.applausegithubapp.data.entity.GithubItem

/**
 * author: Artur Godlewski
 */
@Database(entities = [GithubItem::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun githubItemDao(): GithubItemDao
}
