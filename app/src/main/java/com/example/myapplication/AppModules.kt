package com.example.myapplication

import androidx.room.Room
import com.example.myapplication.data.repository.UserRepository
import com.example.myapplication.data.repository.UserRepositoryImpl
import com.example.myapplication.usecase.account.*
import com.example.myapplication.view.activity.loginactivity.LoginActivityViewModel
import com.example.myapplication.view.activity.registeractivity.RegisterActivityViewModel
import com.example.myapplication.view.fragment.start.StartViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.concurrent.Executor
import java.util.concurrent.Executors

val NETWORK_IO_EXECUTOR = named("network_io_executor")
val DISK_IO_EXECUTOR = named("disk_io_executor")

val ExecutorModule = module {
    single<Executor>(NETWORK_IO_EXECUTOR) { Executors.newFixedThreadPool(4) }
    single<Executor>(DISK_IO_EXECUTOR) { Executors.newSingleThreadExecutor() }
    factory<SecureStorage> { SecureSharedPrefsStorage(get()) }
}

val PersistenceModule = module {
    // Room Database
    single {
        Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "application-db")
            .build()
    }
    // Dao
    single { get<AppDatabase>().userDao() }
    // Repositories
    single<UserRepository> { UserRepositoryImpl(get(), get(DISK_IO_EXECUTOR)) }
}

val ViewModelModule = module {
    viewModel { StartViewModel(get()) }
    viewModel { RegisterActivityViewModel(get()) }
    viewModel { LoginActivityViewModel(get()) }
}

val UsecaseModule = module {
    factory { RegisterAccount(get(), get(), get(DISK_IO_EXECUTOR)) }
    factory { Login(get(), get(), get(DISK_IO_EXECUTOR)) }
    factory { Logout(get(), get(), get(), get(DISK_IO_EXECUTOR)) }
    factory { AuthStateManager(get()) }
}