package com.firan.githubapp

import androidx.room.Room
import com.firan.githubapp.data.repository.GithubItemRepository
import com.firan.githubapp.data.repository.GithubItemRepositoryImpl
import com.firan.githubapp.usecase.connection.ConnectivityCheck
import com.firan.githubapp.usecase.connection.ConnectivityCheckImpl
import com.firan.githubapp.usecase.fetchrepository.FetchRepositoryDetails
import com.firan.githubapp.view.fragment.details.DetailsFragmentViewModel
import com.firan.githubapp.view.fragment.start.StartFragmentViewModel
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
}

val ServiceModule = module {
    factory<ConnectivityCheck> { ConnectivityCheckImpl(get()) }
}