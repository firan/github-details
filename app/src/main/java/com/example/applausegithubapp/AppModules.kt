package com.example.applausegithubapp

import androidx.room.Room
import com.example.applausegithubapp.data.repository.GithubItemRepository
import com.example.applausegithubapp.data.repository.GithubItemRepositoryImpl
import com.example.applausegithubapp.usecase.connection.ConnectivityCheck
import com.example.applausegithubapp.usecase.fetchrepository.FetchRepositoryDetails
import com.example.applausegithubapp.view.fragment.details.DetailsFragmentViewModel
import com.example.applausegithubapp.view.fragment.start.StartFragmentViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * author: Artur Godlewski
 */

val DISK_IO_EXECUTOR = named("disk_io_executor")

val ExecutorModule = module {
    single<Executor>(DISK_IO_EXECUTOR) { Executors.newSingleThreadExecutor() }
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
    viewModel { StartFragmentViewModel(get(), get(), get()) }
    viewModel { (repoId: Int) -> DetailsFragmentViewModel(repoId, get()) }
}

val UsecaseModule = module {
    factory { FetchRepositoryDetails(get()) }
    factory { ConnectivityCheck(get()) }
}