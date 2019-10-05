package com.example.myapplication.usecase.account

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.example.myapplication.AppDatabase
import java.util.concurrent.Executor

class Logout(
    private val context: Context,
    private val authStateManager: AuthStateManager,
    private val database: AppDatabase,
    private val diskIOExecutor: Executor
) {
    fun perform(successHandler: () -> Unit) {
        val mainHandler = Handler(Looper.getMainLooper())
        diskIOExecutor.execute {
            authStateManager.clear()
            context.fileList().forEach { context.deleteFile(it) }
            database.clearAllTables()

            mainHandler.post {
                successHandler()
            }
        }
    }
}