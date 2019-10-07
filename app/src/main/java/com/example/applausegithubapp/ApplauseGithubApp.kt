package com.example.applausegithubapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.EmptyLogger
import timber.log.Timber

/**
 * author: Artur Godlewski
 */
class ApplauseGithubApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ApplauseGithubApp)
            modules(listOf(ExecutorModule, PersistenceModule, ViewModelModule, UsecaseModule, ServiceModule))
            logger(EmptyLogger())
        }
        Timber.plant(Timber.DebugTree())
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }
}