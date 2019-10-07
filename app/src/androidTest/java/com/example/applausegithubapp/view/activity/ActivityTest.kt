package com.example.applausegithubapp.view.activity

import android.app.Activity
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.IdlingRegistry
import com.example.applausegithubapp.ExecutorModule
import com.example.applausegithubapp.PersistenceModule
import com.example.applausegithubapp.UsecaseModule
import com.example.applausegithubapp.ViewModelModule
import com.example.applausegithubapp.mock.MockConnectivityCheck
import com.example.applausegithubapp.usecase.common.FetchingIdlingResource
import com.example.applausegithubapp.usecase.connection.ConnectivityCheck
import org.junit.After
import org.junit.Before
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest

/**
 * author: Artur Godlewski
 */
abstract class ActivityTest<T : Activity> : KoinTest {

    var idlingResource: FetchingIdlingResource? = null
    private lateinit var scenario: ActivityScenario<T>
    val MockedServiceModule = module {
        single<ConnectivityCheck> { MockConnectivityCheck() }
    }

    @Before
    fun initScenario() {
        unloadKoinModules(listOf(ExecutorModule, PersistenceModule, MockedServiceModule, UsecaseModule, ViewModelModule))
        loadKoinModules(listOf(ExecutorModule, PersistenceModule, MockedServiceModule, UsecaseModule, ViewModelModule))
        scenario = startScenario()
    }

    abstract fun startScenario(): ActivityScenario<T>

    @After
    fun closeScenario() {
        IdlingRegistry.getInstance().unregister(idlingResource)
        scenario.close()
    }
}
