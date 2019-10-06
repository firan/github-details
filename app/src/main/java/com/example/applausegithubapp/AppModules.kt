package com.example.applausegithubapp

import androidx.room.Room
import com.example.applausegithubapp.data.repository.GithubItemRepository
import com.example.applausegithubapp.data.repository.GithubItemRepositoryImpl
import com.example.applausegithubapp.usecase.account.*
import com.example.applausegithubapp.usecase.repository.FetchRepositoryDetails
import com.example.applausegithubapp.view.fragment.details.DetailsFragmentViewModel
import com.example.applausegithubapp.view.fragment.start.StartFragmentViewModel
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
    single { get<AppDatabase>().githubItemDao() }
    // Repositories
    single<GithubItemRepository> { GithubItemRepositoryImpl(get(), get(DISK_IO_EXECUTOR)) }
}

val ViewModelModule = module {
    viewModel { StartFragmentViewModel(get(), get()) }
    viewModel { (repoId: Int) -> DetailsFragmentViewModel(repoId, get()) }
}

val UsecaseModule = module {
    factory { FetchRepositoryDetails(get(), get(DISK_IO_EXECUTOR)) }
    factory { AuthStateManager(get()) }
}