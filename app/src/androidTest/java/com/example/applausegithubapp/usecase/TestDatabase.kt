package com.example.applausegithubapp.usecase

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.example.applausegithubapp.AppDatabase

/**
 * author: Artur Godlewski
 */
fun getTestDatabase(): AppDatabase {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    return Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries().build()
}