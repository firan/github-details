package com.example.applausegithubapp.view.activity

import android.app.Activity
import androidx.test.core.app.ActivityScenario
import com.example.applausegithubapp.ExecutorModule
import com.example.applausegithubapp.PersistenceModule
import com.example.applausegithubapp.UsecaseModule
import com.example.applausegithubapp.ViewModelModule
import org.junit.After
import org.junit.Before
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.test.KoinTest

open abstract class ActivityTest<T : Activity> : KoinTest {

    private lateinit var scenario: ActivityScenario<T>

    @Before
    fun initScenario() {
        unloadKoinModules(listOf(ExecutorModule, PersistenceModule, ViewModelModule, UsecaseModule))
        loadKoinModules(listOf(ExecutorModule, PersistenceModule, ViewModelModule, UsecaseModule))
        scenario = startScenario()
    }

    abstract fun startScenario(): ActivityScenario<T>

    @After
    fun closeScenario() {
        scenario.close()
    }
}
