package com.firan.githubapp

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.firan.githubapp.data.converters.Converters
import com.firan.githubapp.data.dao.GithubItemDao
import com.firan.githubapp.data.entity.GithubItem

/**
 * author: Artur Godlewski
 */
@Database(entities = [GithubItem::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun githubItemDao(): GithubItemDao
}
